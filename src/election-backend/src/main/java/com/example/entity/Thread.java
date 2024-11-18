package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "thread")
@Getter
@Setter
@AllArgsConstructor
public class Thread implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotBlank(message = "Title is required")
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @NotBlank(message = "Body is required")
    @Column(name = "body", length = 255, nullable = false)
    private String body;

    @NotBlank(message = "Date is required")
    @Column(name = "date", length = 255, nullable = false)
    private String date;

    @NotBlank(message = "Category is required")
    @Column(name = "category", length = 255, nullable = false)
    private String category;

    public Thread() {
        // Default constructor
    }

    public Thread(String title, String body, String date, String category, User user) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.category = category;
        this.user = user;
    }
}