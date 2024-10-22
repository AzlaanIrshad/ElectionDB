package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "candidate")
public class Candidate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotBlank(message = "Party is required")
    @Column(name = "party", length = 255, nullable = false)
    private String party;

    // Constructors
    public Candidate() {
    }

    public Candidate(String name, String party) {
        this.name = name;
        this.party = party;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    // Override toString for easy debugging
    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", party='" + party + '\'' +
                '}';
    }
}
