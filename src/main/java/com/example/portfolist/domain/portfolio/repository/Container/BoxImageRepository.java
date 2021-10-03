package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.container.BoxImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxImageRepository extends JpaRepository<BoxImage,Long> {
    void deleteByBoxContainerPortfolioUser(User user);
}
