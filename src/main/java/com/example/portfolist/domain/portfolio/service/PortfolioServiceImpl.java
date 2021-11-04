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
    private final ContainerImageRepository containerImageRepository;
    private final ContainerTextRepository containerTextRepository;
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

        return PortfolioResponse.of(portfolio, portfolio.getUser().getPk() == getCurrentUser().getPk(), touched);
    }

    @Override
    @Transactional
    public Long createPortfolio(PortfolioRequest request, MultipartFile file) {
        User user = getCurrentUser();
        Portfolio portfolio = portfolioRepository.save(Portfolio.toEntity(request, user));

        if (file != null) {
            String fileName = fileUploadProvider.uploadFile(file);
            request.setFileName(fileName);
        }

        request.getField().forEach(fieldKindId -> portfolioFieldRepository.save(PortfolioField.toEntity(portfolio, getFieldKind(fieldKindId))));
        request.getMoreInfo().forEach(moreInfoRequest -> moreInfoRepository.save(MoreInfo.toEntity(portfolio, moreInfoRequest)));

        for (CertificateRequest certificateRequest : request.getCertificateContainerList()) {
            CertificateContainer certificateContainer = certificateContainerRepository.save(CertificateContainer.toEntity(certificateRequest, portfolio));
            certificateRequest.getCertificateList().forEach(s -> certificateRepository.save(Certificate.builder()
                    .certificateContainer(certificateContainer).content(s).build()));
        }

        return portfolio.getPk();
    }

    @Override
    public void createContainer(ContainerRequest containerRequest, List<MultipartFile> containerImgList) {
        Portfolio portfolio = getPortfolio(containerRequest.getPortfolioId());

        Container container = containerRepository.save(Container.toEntity(containerRequest, portfolio));

        containerImgList.forEach(image -> containerImageRepository.save(ContainerImage.toEntity(container, fileUploadProvider.uploadFile(image))));

        containerRequest.getContainerTextList().forEach(containerText -> containerTextRepository.save(ContainerText.toEntity(container, containerText)));
    }

    @Override
    public void deletePortfolio(long portfolioId) {
        Portfolio portfolio = getPortfolio(portfolioId);

        if (!(portfolio.getUser().getPk() == getCurrentUser().getPk())) throw new PermissionDeniedException();

        fileUploadProvider.deleteFile(portfolio.getUrl());
        portfolio.getContainerList()
                .forEach(container -> container.getContainerImageList()
                        .forEach(containerImage -> fileUploadProvider.deleteFile(containerImage.getUrl())));

        portfolioRepository.deleteById(portfolioId);
    }

    @Override
    public Long updatePortfolio(long portfolioId, PortfolioRequest request, MultipartFile file) {
        Portfolio portfolio = getPortfolio(portfolioId);
        if (file != null) {
            fileUploadProvider.deleteFile(portfolio.getUrl());
            String fileName = fileUploadProvider.uploadFile(file);
            request.setFileName(fileName);
        }
        portfolio.update(request);
        portfolio.getPortfolioFields().clear();
        containerRepository.deleteByPortfolioPk(portfolioId);
        certificateContainerRepository.deleteByPortfolioPk(portfolioId);
        moreInfoRepository.deleteByPortfolioPk(portfolioId);

        return portfolioId;
    }

    @Override
    public void updateContainer(long portfolioId, ContainerRequest request, MultipartFile file) {

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

    private FieldKind getFieldKind(Integer fieldKindId) {
        return fieldKindRepository.findById(fieldKindId).orElseThrow(FieldNotFoundException::new);
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
