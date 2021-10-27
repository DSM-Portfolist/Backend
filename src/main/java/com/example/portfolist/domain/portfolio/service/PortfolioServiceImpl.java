package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.FieldNotFoundException;
import com.example.portfolist.domain.auth.repository.repository.FieldKindRepository;
import com.example.portfolist.domain.portfolio.dto.request.ContainerRequest;
import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.*;
import com.example.portfolist.domain.portfolio.entity.Certificate;
import com.example.portfolist.domain.portfolio.entity.MoreInfo;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.PortfolioField;
import com.example.portfolist.domain.portfolio.entity.container.Box;
import com.example.portfolist.domain.portfolio.entity.container.BoxImage;
import com.example.portfolist.domain.portfolio.entity.container.BoxText;
import com.example.portfolist.domain.portfolio.entity.container.Container;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.CertificateRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxImageRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxTextRepository;
import com.example.portfolist.domain.portfolio.repository.Container.ContainerRepository;
import com.example.portfolist.domain.portfolio.repository.MoreInfoRepository;
import com.example.portfolist.domain.portfolio.repository.PortfolioFieldRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.global.error.exception.PermissionDeniedException;
import com.example.portfolist.global.file.FileUploadProvider;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService{

    private final PortfolioRepository portfolioRepository;
    private final ContainerRepository containerRepository;
    private final BoxRepository boxRepository;
    private final BoxImageRepository boxImageRepository;
    private final BoxTextRepository boxTextRepository;
    private final PortfolioFieldRepository portfolioFieldRepository;
    private final FieldKindRepository fieldKindRepository;
    private final MoreInfoRepository moreInfoRepository;
    private final CertificateRepository certificateRepository;

    private final FileUploadProvider fileUploadProvider;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public PortfolioListResponse getPortfolioList(Pageable page, List<String> fieldList) {

        Page<PortfolioPreview> portfolioList = portfolioRepository.getPortfolioList(page, fieldList);

        return new PortfolioListResponse(
                portfolioList.getTotalElements(),
                portfolioList.getContent()
        );
    }

    @Override
    public PortfolioResponse getPortfolio(long projectId) {
        return null;
    }

    @Override
    @Transactional
    public void createPortfolio(PortfolioRequest request, List<List<MultipartFile>> boxImgListList) {
        User user = authenticationFacade.getUser();
        Portfolio portfolio = portfolioRepository.save(PortfolioRequest.of(request, user));

        for (int i = 0; i < request.getContainerList().size(); i++) {
            ContainerRequest containerRequest = request.getContainerList().get(i);

            Container container = containerRepository.save(Container.of(containerRequest, portfolio));

            Box box = boxRepository.save(Box.of(container));

            boxImgListList.get(i).forEach(boxImage -> boxImageRepository.save(BoxImage.of(box, fileUploadProvider.uploadFile(boxImage))));
            containerRequest.getBoxList().forEach(boxRequest -> boxTextRepository.save(BoxText.of(box, boxRequest)));
        }

        request.getField().forEach(fieldKindId -> portfolioFieldRepository.save(PortfolioField.of(portfolio, getFieldKind(fieldKindId))));
        request.getMoreInfoRequestList().forEach(moreInfoRequest -> moreInfoRepository.save(MoreInfo.of(portfolio, moreInfoRequest)));
        request.getCertificate().forEach(certificate -> certificateRepository.save(Certificate.of(portfolio, certificate)));
    }

    private FieldKind getFieldKind(Integer fieldKindId) {
        return fieldKindRepository.findById(fieldKindId).orElseThrow(FieldNotFoundException::new);
    }

    @Override
    public void deletePortfolio(long portfolioId) {
        if (checkPermission(portfolioId, authenticationFacade.getUser())) throw new PermissionDeniedException();

        portfolioRepository.deleteById(portfolioId);
    }

    @Override
    public void updatePortfolio(long portfolioId) {

    }

    private boolean checkPermission(long portfolioId, User user) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(PortfolioNotFoundException::new);
        return portfolio.getUser().getPk() != user.getPk();
    }

    @Override
    public RecentPortfolioResponse getRecentPortfolio(int size) {
        return null;
    }

    @Override
    public ThisMonthPortfolioResponse getThisMonthPortfolio() {
        return null;
    }

    @Override
    public PortfolioListResponse getPortfolioByUser(long userId) {
        return null;
    }
}
