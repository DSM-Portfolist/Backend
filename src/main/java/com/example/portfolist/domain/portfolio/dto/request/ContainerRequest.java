package com.example.portfolist.domain.portfolio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContainerRequest {

    private long portfolioId;

    private String containerTitle;

    private List<MultipartFile> containerImgList;

    private List<BoxRequest> containerTextList;
}
