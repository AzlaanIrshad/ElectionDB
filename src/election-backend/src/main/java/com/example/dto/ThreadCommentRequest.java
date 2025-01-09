package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ThreadCommentRequest {
    private String body;
    private String date;
    private String email;

    public ThreadComment toEntity() {
        Thread thread = new Thread();
        thread.setBody(body);
        thread.setDate(date);
        thread.setEmail(email);
        return ThreadComment;
    }
}

