package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "thread")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Thread implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ManyToMany
    @JoinTable(
            name = "thread_category_map", // Join table name
            joinColumns = @JoinColumn(name = "thread_id"), // Thread ID column
            inverseJoinColumns = @JoinColumn(name = "category_id") // Category ID column
    )
    @Builder.Default
    private Set<ThreadCategory> categories = new HashSet<>();

    public Thread(String title, String body, String date, User user, Set<ThreadCategory> categories) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.user = user;
        this.categories = categories;
    }
}
