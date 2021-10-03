package com.example.portfolist.domain.portfolio.entity.touching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TouchingId implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "portfolio_id")
    private long portfolioId;

}
