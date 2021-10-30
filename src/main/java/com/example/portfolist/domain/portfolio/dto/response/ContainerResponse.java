package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.container.Container;
import com.example.portfolist.domain.portfolio.entity.container.ContainerImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerResponse {

    private String containerTitle;

    private List<String> containerImg;

    private List<ContainerTextResponse> containerTextList;

    public static ContainerResponse of(Container container) {
        return ContainerResponse.builder()
                .containerTitle(container.getTitle())
                .containerImg(container.getContainerImageList().stream()
                        .map(ContainerImage::getUrl)
                        .collect(Collectors.toList()))
                .containerTextList(container.getContainerTextList().stream()
                        .map(ContainerTextResponse::of)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
