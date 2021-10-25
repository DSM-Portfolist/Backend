package com.example.portfolist.domain.portfolio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoxRequest {
    private String boxTitle;
    private String boxContent;
}