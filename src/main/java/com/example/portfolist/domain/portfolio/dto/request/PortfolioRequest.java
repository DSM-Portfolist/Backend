package com.example.portfolist.domain.portfolio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String content;

    private String name;

    private String introduce;

    private String title;

    private String fileName;

    private Boolean isOpen;

    private List<ContainerRequest> containerList;

}
