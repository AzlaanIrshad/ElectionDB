package com.example.service;

import com.example.entity.Thread;
import com.example.entity.ThreadComment;
import com.example.entity.ThreadCategory;
import com.example.entity.User;
import com.example.repository.ThreadRepository;
import com.example.repository.ThreadCommentRepository;
import com.example.repository.ThreadCategoryRepository;
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
        // Handle category association
        Set<ThreadCategory> categories = new HashSet<>();

        for (String categoryName : categoryNames) {
            // Check if category exists, if not, create it
            ThreadCategory category = threadCategoryRepository.findByName(categoryName)
                    .orElseGet(() -> threadCategoryRepository.save(new ThreadCategory(categoryName)));
            categories.add(category);
        }

        // Fetch the user by email
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        // Create a new thread
        Thread thread = new Thread(title, body, date, user);
        thread.setCategories(categories);

        // Save the thread
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
            throw new RuntimeException("Thread not found with id: " + id);  // Handle appropriately
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

    public ThreadComment createComment(Long threadId, ThreadComment comment) {
        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found with id: " + threadId));
        comment.setThread(thread);
        return threadCommentRepository.save(comment);
    }
}
