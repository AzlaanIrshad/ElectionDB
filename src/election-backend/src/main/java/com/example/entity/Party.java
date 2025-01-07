package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "party")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Party implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotBlank(message = "Description is required")
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    public Party(String name, String description) {
        this.name = name;
        this.description = description;
    }
}