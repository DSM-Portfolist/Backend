package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.container.BoxText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxResponse {

    private String boxTitle;

    private String boxContent;

    public static BoxResponse toDto(BoxText boxText) {
        return BoxResponse.builder()
                .boxTitle(boxText.getTitle())
                .boxContent(boxText.getContent())
                .build();
    }
}
