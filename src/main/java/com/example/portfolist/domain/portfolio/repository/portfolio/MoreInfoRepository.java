package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.portfolio.MoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoreInfoRepository extends JpaRepository<MoreInfo, Long> {

    void deleteByPortfolioUser(User user);

}
