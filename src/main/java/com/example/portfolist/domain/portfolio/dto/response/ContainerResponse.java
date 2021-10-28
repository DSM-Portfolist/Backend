package com.example.portfolist.domain.portfolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerResponse {

    private String containerTitle;

    private List<String> boxImg;

    private List<BoxResponse> boxList;
}
