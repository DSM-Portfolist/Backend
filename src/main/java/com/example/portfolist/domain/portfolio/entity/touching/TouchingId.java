package com.example.portfolist.domain.portfolio.entity.touching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TouchingId implements Serializable {

    private long user;
    private long portfolio;
}
