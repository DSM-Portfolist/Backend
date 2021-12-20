package com.example.portfolist.domain.portfolio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MoreInfoRequest {
    @NotNull
    private String name;

    @NotNull
    private String content;
}
