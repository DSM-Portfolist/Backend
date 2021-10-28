package com.example.portfolist.domain.portfolio.entity.container;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class BoxImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @Column(length = 100, nullable = false)
    private String url;

    public static BoxImage of(Box box, String boxImgName) {
        return BoxImage.builder()
                .box(box)
                .url(boxImgName)
                .build();
    }
}
