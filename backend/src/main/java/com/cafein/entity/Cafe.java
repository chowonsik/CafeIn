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

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "branch",length = 100)
    private String branch;

    @Column(name = "area",length = 100)
    private String area;

    @Column(name = "tel", length = 45)
    private String tel;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "latitude", length = 30)
    private String latitude;

    @Column(name = "longitude", length = 30)
    private String longitude;

    @Column(name = "img_url")
    private String imgUrl;

}
