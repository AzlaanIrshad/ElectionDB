package com.example.dto;

import com.example.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ThreadRequest {
    private String title;
    private String body;
    private String date;
    private User user;
    private List<String> categories;  // List of category names
}

