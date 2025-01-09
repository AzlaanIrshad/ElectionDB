package com.example.dto;

import com.example.entity.ThreadComment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ThreadCommentRequest {
    private String body;
    private String date;
    private String email;

    public ThreadComment toEntity() {
        ThreadComment threadComment = new ThreadComment();
        threadComment.setBody(body);
        threadComment.setDate(date);
        return threadComment;
    }
}

