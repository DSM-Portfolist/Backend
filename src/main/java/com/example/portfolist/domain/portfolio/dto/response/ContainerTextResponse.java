package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.container.ContainerText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerTextResponse {

    private String boxTitle;

    private String boxContent;

    public static ContainerTextResponse of(ContainerText boxText) {
        return ContainerTextResponse.builder()
                .boxTitle(boxText.getTitle())
                .boxContent(boxText.getContent())
                .build();
    }
}
