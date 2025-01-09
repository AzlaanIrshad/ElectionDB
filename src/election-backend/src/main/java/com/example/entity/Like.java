package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Like implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @NotNull(message = "Vote type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", length = 20, nullable = false)
    private VoteType voteType;

    @NotNull(message = "Created at is required")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "thread_id", referencedColumnName = "id", nullable = false)
    private Thread thread;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    /**
     * Enum for vote types (e.g., UPVOTE or DOWNVOTE).
     */
    public enum VoteType {
        UPVOTE,
        DOWNVOTE
    }

    /**
     * Constructor to create a Like instance with mandatory fields.
     *
     * @param user      User who cast the vote.
     * @param voteType  Type of vote (UPVOTE or DOWNVOTE).
     * @param thread    Thread where the vote was cast.
     */
    public Like(User user, VoteType voteType, Thread thread) {
        this.user = user;
        this.voteType = voteType;
        this.thread = thread;
        this.createdAt = LocalDateTime.now();
    }
}
