package com.example.portfolist.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @OneToOne
    @JoinColumn(name = "normalUser_id")
    private NormalUser normalUser;

    @Column(name = "github_id")
    private String githubId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduce")
    private String introduce;

}
