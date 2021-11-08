package com.example.portfolist.domain.portfolio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContainerRequest {

    @NotNull
    private String containerTitle;

    private List<String> containerImgList;

    private List<ContainerTextRequest> containerTextList;
}
