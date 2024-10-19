package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

@Entity
@Table(name = "thread")
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

    // setters and getters are going to be replaced by Lombok annotations
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}