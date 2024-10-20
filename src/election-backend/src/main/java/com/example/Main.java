package com.example;

import com.example.entity.User;
import com.example.entity.Thread;
import com.example.repository.UserRepository;
import com.example.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadRepository threadRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        // Create users with appropriate roles
        User regularUser = new User("gaga", "gaga@example.com", "password123", User.Role.USER);
        User regularUser = new User("piet", "kzt@example.com", "password123", User.Role.USER);
        User regularUser = new User("azlaan", "azlaaniscute@example.com", "password123", User.Role.USER);
        User regularUser = new User("dragonslayer", "pakkoe@example.com", "password123", User.Role.USER);
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