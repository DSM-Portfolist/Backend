package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.container.BoxText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxTextRepository extends JpaRepository<BoxText,Long> {

    void deleteByBoxContainerPortfolioUser(User user);

}
