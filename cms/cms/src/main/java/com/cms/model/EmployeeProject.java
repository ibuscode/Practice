package com.cms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employee_project")
@Getter
@Setter

public class EmployeeProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate allottedDate;
    private String roleDetails;
    private int duration;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Project project;
}
