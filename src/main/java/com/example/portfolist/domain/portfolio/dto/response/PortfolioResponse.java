package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.Certificate;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {

    private long userId;

    private String name;

    private String profileImg;

    private LocalDate createDate;

    private Boolean touched;

    private Boolean isMine;

    private int totalTouching;

    private String icon;

    private String title;

    private String introduce;

    private List<String> field;

    private List<MoreInfoResponse> moreInfoList;

    private List<ContainerResponse> containerList;

    private List<CertificateContainerResponse> certificateContainerList;

    private String link;

    private String file;

    public static PortfolioResponse of(Portfolio portfolio, Boolean isMine, Boolean touched) {
        return PortfolioResponse.builder()
                .userId(portfolio.getUser().getPk())
                .name(portfolio.getUser().getName())
                .profileImg(portfolio.getUser().getUrl())
                .createDate(portfolio.getDate())
                .touched(touched)
                .isMine(isMine)
                .totalTouching(portfolio.getTouchingList().size())
                .icon(portfolio.getMainIcon())
                .title(portfolio.getTitle())
                .introduce(portfolio.getIntroduce())
                .field(portfolio.getPortfolioFields().stream()
                        .map(portfolioField -> portfolioField.getFieldKind().getContent())
                        .collect(Collectors.toList()))
                .moreInfoList(portfolio.getMoreInfoList().stream()
                        .map(MoreInfoResponse::of)
                        .collect(Collectors.toList()))
                .containerList(portfolio.getContainerList().stream()
                        .map(ContainerResponse::of)
                        .collect(Collectors.toList()))
                .certificateContainerList(portfolio.getCertificateContainerList().stream()
                        .map(certificateContainer -> CertificateContainerResponse.builder()
                                .title(certificateContainer.getTitle())
                                .certificateList(certificateContainer.getCertificateList().stream()
                                        .map(Certificate::getContent)
                                        .collect(Collectors.toList())).build())
                        .collect(Collectors.toList()))
                .link(portfolio.getLink())
                .file(portfolio.getUrl())
                .build();
    }
}
