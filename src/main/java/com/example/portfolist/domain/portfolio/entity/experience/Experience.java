package com.example.portfolist.domain.portfolio.entity.experience;

import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "experience")
    private List<ExperienceImage> experienceImageList;

    @OneToMany(mappedBy = "experience")
    private List<ExperienceText> experienceTextList;
}
