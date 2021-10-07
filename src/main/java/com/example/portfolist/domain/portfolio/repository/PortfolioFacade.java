package com.example.portfolist.domain.portfolio.repository;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.repository.Container.BoxImageRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxTextRepository;
import com.example.portfolist.domain.portfolio.repository.Container.ContainerRepository;
import com.example.portfolist.domain.portfolio.repository.comment.CommentRepository;
import com.example.portfolist.domain.portfolio.repository.comment.ReCommentRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.CertificateRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.MoreInfoRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioFieldRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.domain.portfolio.repository.touching.TouchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortfolioFacade {

    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private final BoxImageRepository boxImageRepository;
    private final BoxRepository boxRepository;
    private final BoxTextRepository boxTextRepository;
    private final ContainerRepository containerRepository;
    private final CertificateRepository certificateRepository;
    private final MoreInfoRepository moreInfoRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioFieldRepository portfolioFieldRepository;
    private final TouchingRepository touchingRepository;

    public Page<Touching> findTouchingAll(int page, int size, User user) {
        return touchingRepository.findByUser(PageRequest.of(page, size), user);
    }

    public void deleteMoreInfoByUser(User user) {
        moreInfoRepository.deleteByPortfolioUser(user);
    }

    public void deleteBoxImageByUser(User user) {
        boxImageRepository.deleteByBoxContainerPortfolioUser(user);
    }

    public void deleteBoxTextByUser(User user) {
        boxTextRepository.deleteByBoxContainerPortfolioUser(user);
    }

    public void deleteBoxByUser(User user) {
        boxRepository.deleteByContainerPortfolioUser(user);
    }

    public void deleteContainerByUser(User user) {
        containerRepository.deleteByPortfolioUser(user);
    }

    public void deleteCertificateByUser(User user) {
        certificateRepository.deleteByPortfolioUser(user);
    }

    public void deletePortfolioByUser(User user) {
        portfolioRepository.deleteByUser(user);
    }

    public void deleteCommentByUser(User user) {
        commentRepository.deleteByUser(user);
    }

    public void deleteReCommentByUser(User user) {
        reCommentRepository.deleteByCommentUser(user);
    }

    public void deletePortfolioFieldByUser(User user) {
        portfolioFieldRepository.deleteByPortfolioUser(user);
    }

    public void deleteTouchingByUser(User user) {
        touchingRepository.deleteByUser(user);
        touchingRepository.deleteByPortfolioUser(user);
    }

}
