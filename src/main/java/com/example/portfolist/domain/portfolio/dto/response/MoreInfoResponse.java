package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.MoreInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoreInfoResponse {
    private String name;
    private String content;

    public static MoreInfoResponse of(MoreInfo moreInfo) {
        return MoreInfoResponse.builder()
                .name(moreInfo.getName())
                .content(moreInfo.getContent())
                .build();
    }
}