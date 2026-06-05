package com.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String appliedAt;

    @ManyToOne
    private JobSeeker jobSeeker;
    @ManyToOne
    private Job job;

}
