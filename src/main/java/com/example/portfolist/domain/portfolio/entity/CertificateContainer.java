package com.example.portfolist.domain.portfolio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CertificateContainer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pk;

    private String title;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "certificateContainer", cascade = CascadeType.REMOVE)
    private List<Certificate> certificateList;
}