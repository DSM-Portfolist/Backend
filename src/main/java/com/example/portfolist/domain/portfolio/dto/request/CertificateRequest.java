package com.example.portfolist.domain.portfolio.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CertificateRequest {
    private String title;

    private List<String> certificateList;
}
