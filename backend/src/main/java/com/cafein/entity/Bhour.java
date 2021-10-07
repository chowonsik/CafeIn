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

    @Column(name = "type", nullable=false)
    private int type;

    @Column(name = "week_type", nullable=false)
    private int weekType;
    
    @Column(name = "mon", nullable=false)
    private int mon;
    
    @Column(name = "tue", nullable=false)
    private int tue;
    
    @Column(name = "wed", nullable=false)
    private int wed;
    
    @Column(name = "thu", nullable=false)
    private int thu;
    
    @Column(name = "fri", nullable=false)
    private int fri;
    
    @Column(name = "sat", nullable=false)
    private int sat;
    
    @Column(name = "sun", nullable=false)
    private int sun;
    
    @Column(name = "start_time", nullable=false)
    private String startTime;

    @Column(name = "end_time", nullable=false)
    private String endTime;

    @Column(name = "etc", nullable=false)
    private String etc;
    
}
