package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "threadComment")
@Getter
@Setter
@AllArgsConstructor
public class ThreadComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "thread_id", referencedColumnName = "id", nullable = false)
    private Thread thread;

    @NotBlank(message = "Body is required")
    @Column(name = "body", length = 255, nullable = false)
    private String body;

    @NotBlank(message = "Date is required")
    @Column(name = "date", length = 255, nullable = false)
    private String date;

    @NotBlank(message = "Category is required")
    @Column(name = "category", length = 255, nullable = false)
    private String category;

    public ThreadComment() {
        // Default constructor
    }

    public ThreadComment(User user, Thread thread, String body, String date, String category) {
        this.user = user;
        this.thread = thread;
        this.body = body;
        this.date = date;
        this.category = category;
    }
}