package com.example.portfolist.domain.portfolio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioRequest {

    private String icon;

    private String link;

    private List<Integer> field;

    private List<CertificateRequest> certificateContainerList;

    private List<MoreInfoRequest> moreInfo;

    @NotNull
    private String introduce;

    @NotNull
    private String title;

    private String file;

    private boolean isOpen;

    private String thumbnail;

    private List<ContainerRequest> containerList;

}
