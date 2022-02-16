package com.example.portfolist.domain.portfolio.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "certificateContainer_id", nullable = false)
    private CertificateContainer certificateContainer;

    @Column(length = 45)
    private String content;

    public static Certificate toEntity(CertificateContainer certificateContainer, String certificate) {
        return Certificate.builder()
                .certificateContainer(certificateContainer)
                .content(certificate)
                .build();
    }
}
