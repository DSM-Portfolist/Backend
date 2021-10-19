package com.example.portfolist.domain.portfolio.entity.comment;

import com.example.portfolist.domain.auth.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ReComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column
    private LocalDate date;

    @Column(length = 300)
    private String content;
}
