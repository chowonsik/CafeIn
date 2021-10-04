package com.cafein.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@Table(name = "tags")
public class Tags {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="cafe_id", nullable=false)    
    private Cafe cafe;

    @Column(name = "taste")
    private int taste;

    @Column(name = "view")
    private int view;
    
    @Column(name = "mood")
    private int mood;
    
    @Column(name = "wide")
    private int wide;
    
    @Column(name = "study")
    private int study;
    
    @Column(name = "nice")
    private int nice;
    
}
