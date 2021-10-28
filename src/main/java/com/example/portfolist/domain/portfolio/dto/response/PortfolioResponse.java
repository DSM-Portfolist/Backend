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

    private int totalTouching;

    private Character icon;

    private String title;

    private String introduce;

    private List<String> field;

    private List<MoreInfoResponse> moreInfoList;

    private List<ContainerResponse> containerList;

    private List<String> certificate;

    private String link;

    private String file;

    private List<CommentResponse> commentList;

    public static PortfolioResponse of(Portfolio portfolio, List<ContainerResponse> containerList, List<CommentResponse> commentList) {
        return PortfolioResponse.builder()
                .userId(portfolio.getUser().getPk())
                .name(portfolio.getUser().getName())
                .profileImg(portfolio.getUser().getUrl())
                .createDate(portfolio.getDate())
                .touched(false)
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
                .containerList(containerList)
                .certificate(portfolio.getCertificateList().stream()
                        .map(Certificate::getContent)
                        .collect(Collectors.toList()))
                .link(portfolio.getLink())
                .file(portfolio.getUrl())
                .commentList(commentList)
                .build();
    }
}
