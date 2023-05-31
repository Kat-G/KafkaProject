package com.example.KafkaProject.entity;

import javax.persistence.*;
import lombok.Data;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;

@Data
@Entity
@Table(name = "employees")
public class EmployeeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String post;

    @Column(nullable = false)
    private Integer age;
}