package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.request.BoxRequest;
import com.example.portfolist.domain.portfolio.dto.request.ContainerRequest;
import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.*;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.container.Box;
import com.example.portfolist.domain.portfolio.entity.container.BoxImage;
import com.example.portfolist.domain.portfolio.entity.container.BoxText;
import com.example.portfolist.domain.portfolio.entity.container.Container;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.Container.BoxImageRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxTextRepository;
import com.example.portfolist.domain.portfolio.repository.Container.ContainerRepository;
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
    public void createPortfolio(PortfolioRequest request) {
        User user = authenticationFacade.getUser();
        Portfolio portfolio = portfolioRepository.save(PortfolioRequest.of(request, user));

        for (ContainerRequest containerRequest : request.getContainerList()) {
            Container container = containerRepository.save(Container.of(containerRequest, portfolio));

            Box box = boxRepository.save(Box.of(container));

            if (containerRequest.getBoxImgList() != null)
            for (MultipartFile file : containerRequest.getBoxImgList()) {
                String boxImgName = fileUploadProvider.uploadFile(file);
                boxImageRepository.save(BoxImage.of(box, boxImgName));
            }

            for ( BoxRequest boxRequest : containerRequest.getBoxList()) {
                boxTextRepository.save(BoxText.of(box, boxRequest));
            }
        }
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
