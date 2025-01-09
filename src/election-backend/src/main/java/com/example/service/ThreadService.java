package com.example.service;

import com.example.dto.ThreadCommentRequest;
import com.example.entity.Thread;
import com.example.entity.ThreadCategory;
import com.example.entity.ThreadComment;
import com.example.entity.User;
import com.example.repository.ThreadCategoryRepository;
import com.example.repository.ThreadCommentRepository;
import com.example.repository.ThreadRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ThreadCommentRepository threadCommentRepository;

    @Autowired
    private ThreadCategoryRepository threadCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Thread createThread(String title, String body, String date, List<String> categoryNames, String userEmail) {

        Set<ThreadCategory> categories = new HashSet<>();

        for (String categoryName : categoryNames) {

            ThreadCategory category = threadCategoryRepository.findByName(categoryName)
                    .orElseGet(() -> threadCategoryRepository.save(new ThreadCategory(categoryName)));
            categories.add(category);
        }

        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Thread thread = new Thread(title, body, date, user);
        thread.setCategories(categories);

        return threadRepository.save(thread);
    }


    public List<Thread> getThreads() {
        return threadRepository.findAll();
    }

    public Thread getThreadById(Long id) {
        Optional<Thread> optionalThread = threadRepository.findById(id);

        if (optionalThread.isPresent()) {
            return optionalThread.get();
        } else {
            throw new RuntimeException("Thread not found with id: " + id);
        }
    }

    public boolean deleteThread(Long id) {
        if (threadRepository.existsById(id)) {
            threadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ThreadComment> getComments(Long threadId) {
        return threadCommentRepository.findByThreadId(threadId);
    }

    public ThreadComment createComment(Long threadId, ThreadCommentRequest commentRequest) {
        String email = commentRequest.getEmail();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            System.out.println("User not found for email: " + email);
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found with id: " + threadId));

        ThreadComment comment = commentRequest.toEntity();
        comment.setUser(user);
        comment.setThread(thread);

        return threadCommentRepository.save(comment);
    }
}
