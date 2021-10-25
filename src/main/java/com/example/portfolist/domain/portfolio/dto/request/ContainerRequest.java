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
    private String containerTitle;

    private List<MultipartFile> boxImgList;

    private List<BoxRequest> boxList;
}
