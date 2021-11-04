package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.FieldNotFoundException;
import com.example.portfolist.domain.auth.exception.UserNotFoundException;
import com.example.portfolist.domain.auth.repository.repository.FieldKindRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.portfolio.dto.request.CertificateRequest;
import com.example.portfolist.domain.portfolio.dto.request.ContainerRequest;
import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.*;
import com.example.portfolist.domain.portfolio.entity.*;
import com.example.portfolist.domain.portfolio.entity.container.ContainerImage;
import com.example.portfolist.domain.portfolio.entity.container.ContainerText;
import com.example.portfolist.domain.portfolio.entity.container.Container;
import com.example.portfolist.domain.portfolio.entity.touching.TouchingId;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.*;
import com.example.portfolist.domain.portfolio.repository.Container.ContainerImageRepository;
import com.example.portfolist.domain.portfolio.repository.Container.ContainerTextRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService{

    private final PortfolioRepository portfolioRepository;
    private final ContainerRepository containerRepository;
    private final ContainerImageRepository boxImageRepository;
    private final ContainerTextRepository boxTextRepository;
    private final PortfolioFieldRepository portfolioFieldRepository;
    private final FieldKindRepository fieldKindRepository;
    private final MoreInfoRepository moreInfoRepository;
    private final CertificateContainerRepository certificateContainerRepository;
    private final CertificateRepository certificateRepository;
    private final TouchingRepository touchingRepository;
    private final UserRepository userRepository;

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
    @Transactional
    public PortfolioResponse getPortfolioInfo(long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(PortfolioNotFoundException::new);
        Boolean touched = touchingRepository.findById(new TouchingId(getCurrentUser().getPk(), portfolioId)).isPresent();

        return PortfolioResponse.of(portfolio, isItMine(portfolioId), touched);
    }

    @Override
    @Transactional
    public void createPortfolio(PortfolioRequest request, MultipartFile file, List<List<MultipartFile>> boxImgListList) {
        User user = getCurrentUser();
        Portfolio portfolio = portfolioRepository.save(PortfolioRequest.of(request, user));

        if (file != null) {
            String fileName = fileUploadProvider.uploadFile(file);
            request.setFileName(fileName);
        }

        for (int i = 0; i < request.getContainerList().size(); i++) {
            ContainerRequest containerRequest = request.getContainerList().get(i);

            Container container = containerRepository.save(Container.of(containerRequest, portfolio));

            if (boxImgListList != null)
                boxImgListList.get(i).forEach(boxImage -> boxImageRepository.save(ContainerImage.of(container, fileUploadProvider.uploadFile(boxImage))));

            if (containerRequest.getBoxList() != null)
                containerRequest.getBoxList().forEach(boxRequest -> boxTextRepository.save(ContainerText.of(container, boxRequest)));
        }

        request.getField().forEach(fieldKindId -> portfolioFieldRepository.save(PortfolioField.of(portfolio, getFieldKind(fieldKindId))));
        request.getMoreInfo().forEach(moreInfoRequest -> moreInfoRepository.save(MoreInfo.of(portfolio, moreInfoRequest)));

        for (CertificateRequest certificateRequest : request.getCertificateContainerList()) {
            CertificateContainer certificateContainer = certificateContainerRepository
                    .save(CertificateContainer.builder()
                            .portfolio(portfolio)
                            .title(certificateRequest.getTitle())
                            .build());
            certificateRequest.getCertificateList().forEach(s -> certificateRepository.save(Certificate.builder()
                    .certificateContainer(certificateContainer).content(s).build()));
        }
    }

    private FieldKind getFieldKind(Integer fieldKindId) {
        return fieldKindRepository.findById(fieldKindId).orElseThrow(FieldNotFoundException::new);
    }

    @Override
    public void deletePortfolio(long portfolioId) {
        if (!isItMine(portfolioId)) throw new PermissionDeniedException();

        Portfolio portfolio = getPortfolio(portfolioId);

        fileUploadProvider.deleteFile(portfolio.getUrl());
        portfolio.getContainerList()
                .forEach(container -> container.getContainerImageList()
                        .forEach(containerImage -> fileUploadProvider.deleteFile(containerImage.getUrl())));

        portfolioRepository.deleteById(portfolioId);
    }

    @Override
    public void updatePortfolio(long portfolioId) {

    }

    private boolean isItMine(long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(PortfolioNotFoundException::new);
        return portfolio.getUser().getPk() == getCurrentUser().getPk();
    }

    @Override
    public List<RecentPortfolioResponse> getRecentPortfolio(Pageable pageable) {
        return portfolioRepository.findAllByOrderByDateDesc(pageable).getContent().stream()
                .map(RecentPortfolioResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public ThisMonthPortfolioResponse getThisMonthPortfolio() {
        Portfolio thisMonthPortfolio = portfolioRepository.findThisMonthPortfolio();

        return thisMonthPortfolio != null ? ThisMonthPortfolioResponse.of(thisMonthPortfolio) : null;
    }

    @Override
    @Transactional
    public List<PortfolioPreview> getPortfolioByUser(long userId) {
        return portfolioRepository.findAllByUser(getUserById(userId));
    }

    @Override
    public List<PortfolioPreview> getMyTouchingPortfolio(Pageable pageable, long userId) {
        return portfolioRepository.findMyTouchingPortfolio(pageable, getUserById(userId));
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private User getCurrentUser() {
        return authenticationFacade.getUser();
    }

    private Portfolio getPortfolio(long portfolioId) {
        return portfolioRepository.findById(portfolioId).orElseThrow(PortfolioNotFoundException::new);
    }
}
