package com.example.portfolist.domain.mypage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeType {
    TOUCHING,
    COMMENT,
    RECOMMENT,
    P_ADD,
    P_MODIFY
}
