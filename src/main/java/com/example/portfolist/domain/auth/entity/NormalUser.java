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
@Table(name = "normal_user")
@Entity
public class NormalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "normalUser")
    private User user;

    public void changePassword(String password) {
        this.password = password;
    }

}
