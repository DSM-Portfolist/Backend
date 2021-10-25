package com.example.portfolist.domain.portfolio.entity.container;

import com.example.portfolist.domain.portfolio.dto.request.BoxRequest;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class BoxText {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;

    public static BoxText of(Box box, BoxRequest boxRequest) {
        return BoxText.builder()
                .box(box)
                .title(boxRequest.getBoxTitle())
                .content(boxRequest.getBoxContent())
                .build();
    }
}
