package com.cafein.entity;

import lombok.*;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import static javax.persistence.GenerationType.*;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@Table(name = "cafe")
public class Cafe {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "branch", nullable=false, length = 100)
    private String branch;

    @Column(name = "area", nullable = false, length = 100)
    private String area;

    @Column(name = "tel", nullable=false, length = 45)
    private String tel;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "latitude", nullable = false, length = 30)
    private String latitude;

    @Column(name = "longitude", nullable = false, length = 30)
    private String longitude;

    @Column(name = "img_url", nullable = false, columnDefinition = "TEXT")
    private String imgUrl;

}
