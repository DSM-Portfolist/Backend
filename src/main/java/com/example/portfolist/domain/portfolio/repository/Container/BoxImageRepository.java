package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.container.Box;
import com.example.portfolist.domain.portfolio.entity.container.BoxImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxImageRepository extends JpaRepository<BoxImage,Long> {

    List<BoxImage> findAllByBox(Box box);
    void deleteByBoxContainerPortfolioUser(User user);
}
