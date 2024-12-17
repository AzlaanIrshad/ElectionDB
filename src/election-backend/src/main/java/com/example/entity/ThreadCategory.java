package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "thread_category")
@Getter
@Setter
public class ThreadCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private Set<Thread> threads = new HashSet<>();

    public ThreadCategory() {
        // Default constructor
    }

    public ThreadCategory(String name) {
        this.name = name;
    }
}
