package com.example.portfolist.domain.auth.entity;

import com.example.portfolist.domain.portfolio.entity.PortfolioField;
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
@Table(name = "field_kind")
@Entity
public class FieldKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "fieldKind", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Field> field;

    @OneToMany(mappedBy = "fieldKind", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PortfolioField> portfolioFieldList;
}
