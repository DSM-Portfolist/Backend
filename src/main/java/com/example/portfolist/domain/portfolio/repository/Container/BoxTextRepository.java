package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.container.Box;
import com.example.portfolist.domain.portfolio.entity.container.BoxText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxTextRepository extends JpaRepository<BoxText,Long> {

    List<BoxText> findAllByBox(Box box);

    void deleteByBoxContainerPortfolioUser(User user);

}
