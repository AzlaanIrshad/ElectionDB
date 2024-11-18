package com.example.service;

import com.example.entity.Faq;
import com.example.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    public List<Faq> searchFaqs(String keyword) {
        return faqRepository.findByQuestionContainingIgnoreCase(keyword);
    }
}
