package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.container.ContainerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContainerImageRepository extends JpaRepository<ContainerImage,Long> {

    List<ContainerImage> findByContainerPortfolioId(long portfolioId);
    void deleteByContainerPortfolioUser(User user);
    void deleteByContainerPortfolioId(long portfolioId);
}
