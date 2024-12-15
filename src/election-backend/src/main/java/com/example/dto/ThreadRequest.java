package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ThreadRequest {
    private String title;
    private String body;
    private String date;
    private String email;
    private List<String> categories;  // List of category names
}
