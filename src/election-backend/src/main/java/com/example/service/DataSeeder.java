package com.example.service;

import com.example.entity.User;
import com.example.entity.Thread;
import com.example.repository.UserRepository;
import com.example.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        // Delete existing users and threads
        threadRepository.deleteAll();
        userRepository.deleteAll();

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

        System.out.println("Users and Threads saved successfully!");
    }
}
