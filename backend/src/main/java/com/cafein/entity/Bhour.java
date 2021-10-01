package com.cafein.entity;

import lombok.*;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import static javax.persistence.GenerationType.*;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@Table(name = "bhour")
public class Bhour {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="cafe_id", nullable=false)    
    private Cafe cafe;

    @Column(name = "type")
    private int type;

    @Column(name = "week_type")
    private int week_type;
    
    @Column(name = "mon")
    private int mon;
    
    @Column(name = "tue")
    private int tue;
    
    @Column(name = "wed")
    private int wed;
    
    @Column(name = "thu")
    private int thu;
    
    @Column(name = "fri")
    private int fri;
    
    @Column(name = "sat")
    private int sat;
    
    @Column(name = "sun")
    private int sun;
    
    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "etc")
    private String etc;
    
}
