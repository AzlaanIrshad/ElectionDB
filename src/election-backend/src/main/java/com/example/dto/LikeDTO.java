package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LikeDTO {
    private Long voteId;
    private String voteType;
    private Long threadId;
    private Long userId;

    public LikeDTO(Long voteId, String voteType, Long threadId, Long userId) {
        this.voteId = voteId;
        this.voteType = voteType;
        this.threadId = threadId;
        this.userId = userId;
    }
}
