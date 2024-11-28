package com.example.controller;

import com.example.entity.Faq;
import com.example.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FaqController {

    @Autowired
    private FaqService faqService;

    @GetMapping
    public List<Faq> getAllFaqs() {
        return faqService.getAllFaqs();
    }

    @GetMapping("/search")
    public List<Faq> searchFaqs(@RequestParam("q") String keyword) {
        return faqService.searchFaqs(keyword);
    }
}
