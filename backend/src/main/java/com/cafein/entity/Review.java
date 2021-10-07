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
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)    
    private User user;
    
    @ManyToOne
    @JoinColumn(name="cafe_id", nullable=false)    
    private Cafe cafe;
    
    @Column(name = "total_score", nullable = false)
    private int totalScore;

    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date created_at;

}
