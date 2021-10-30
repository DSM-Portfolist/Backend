package com.example.portfolist.domain.portfolio.dto.request;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
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

    public static Portfolio of(PortfolioRequest request, User user) {
        return Portfolio.builder()
                .user(user)
                .title(request.getTitle())
                .introduce(request.getIntroduce())
                .link(request.getLink())
                .url(request.getFileName())
                .date(LocalDate.now())
                .isOpen(request.getIsOpen())
                .mainIcon(request.getIcon())
                .build();
    }
}
