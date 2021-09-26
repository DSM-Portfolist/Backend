package com.example.portfolist.domain.portfolio.entity.project;

import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "project")
    private List<ProjectImage> projectImageList;

    @OneToMany(mappedBy = "project")
    private List<ProjectText> projectTextList;
}
