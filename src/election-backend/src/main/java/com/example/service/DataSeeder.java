package com.example.service;

import com.example.entity.ThreadComment;
import com.example.entity.User;
import com.example.entity.Thread;
import com.example.repository.UserRepository;
import com.example.repository.ThreadRepository;
import com.example.repository.ThreadCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ThreadCommentRepository threadCommentRepository;

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        // Delete existing users and threads
        threadRepository.deleteAll();
        userRepository.deleteAll();
        threadCommentRepository.deleteAll();

        // Create users with appropriate roles
        User regularUser = new User("test", "test@test", "test", User.Role.USER);
        User modUser = new User("modUser", "mod@example.com", "modpw", User.Role.MODERATOR);
        User adminUser = new User("adminUser", "admin@example.com", "adminpw", User.Role.ADMIN);

        // Save all users
        userRepository.save(regularUser);
        userRepository.save(modUser);
        userRepository.save(adminUser);

        // Create placeholder threads
        Thread thread1 = new Thread("Thread 1", "Body 1", "2021-09-01", "Category 1", regularUser);
        Thread thread2 = new Thread("Thread 2", "Body 2", "2021-09-02", "Category 2", modUser);
        Thread thread3 = new Thread("Thread 3", "Body 3", "2021-09-03", "Category 3", adminUser);

        // Save all threads
        threadRepository.save(thread1);
        threadRepository.save(thread2);
        threadRepository.save(thread3);

        // Create placeholder comments
        ThreadComment comment1 = new ThreadComment(regularUser, thread1, "Comment 1", "2021-09-01", "Category 1");
        ThreadComment comment2 = new ThreadComment(modUser, thread2, "Comment 2", "2021-09-02", "Category 2");
        ThreadComment comment3 = new ThreadComment(adminUser, thread3, "Comment 3", "2021-09-03", "Category 3");

        // Save all comments
        threadCommentRepository.save(comment1);
        threadCommentRepository.save(comment2);
        threadCommentRepository.save(comment3);


        System.out.println("Users, Threads and ThreadComments saved successfully!");
    }
}
