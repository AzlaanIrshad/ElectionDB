package com.example.service;

import com.example.entity.*;
import com.example.entity.Thread;
import com.example.repository.FaqRepository;
import com.example.repository.ThreadRepository;
import com.example.repository.ThreadCommentRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ThreadCommentRepository threadCommentRepository;

    @Autowired
    private FaqRepository faqRepository;

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        // Declare users outside of the if block for broader scope
        User regularUser;
        User modUser;
        User adminUser;

        // Seed Users, check if they already exist to avoid duplication
        if (userRepository.count() == 0) {
            regularUser = new User("testUser", "test@test", "test", Role.USER);
            modUser = new User("moderatorUser", "mod@mod", "mod", Role.MODERATOR);
            adminUser = new User("adminUser", "admin@admin", "admin", Role.ADMIN);

            // Save all users in one batch operation
            userRepository.saveAll(Arrays.asList(regularUser, modUser, adminUser));
        } else {
            // Retrieve existing users from the database
            regularUser = userRepository.findByUsername("testUser").orElseThrow();
            modUser = userRepository.findByUsername("moderatorUser").orElseThrow();
            adminUser = userRepository.findByUsername("adminUser").orElseThrow();
        }

        // Seed Threads if none exist
        if (threadRepository.count() == 0) {
            Thread thread1 = new Thread("Thread 1", "Body 1", "2021-09-01", regularUser);
            Thread thread2 = new Thread("Thread 2", "Body 2", "2021-09-02", modUser);
            Thread thread3 = new Thread("Thread 3", "Body 3", "2021-09-03", adminUser);

            threadRepository.saveAll(Arrays.asList(thread1, thread2, thread3));
        } else {
            System.out.println("Threads already exist, skipping thread seeding.");
        }

        // Seed Thread Comments if none exist
        if (threadCommentRepository.count() == 0) {
            Thread thread1 = threadRepository.findByTitle("Thread 1").orElseThrow();
            Thread thread2 = threadRepository.findByTitle("Thread 2").orElseThrow();
            Thread thread3 = threadRepository.findByTitle("Thread 3").orElseThrow();

            ThreadComment comment1 = new ThreadComment(regularUser, thread1, "Comment 1", "2021-09-01", "Category 1");
            ThreadComment comment2 = new ThreadComment(modUser, thread2, "Comment 2", "2021-09-02", "Category 2");
            ThreadComment comment3 = new ThreadComment(adminUser, thread3, "Comment 3", "2021-09-03", "Category 3");

            threadCommentRepository.saveAll(Arrays.asList(comment1, comment2, comment3));
        } else {
            System.out.println("Thread Comments already exist, skipping comment seeding.");
        }

        // Seed FAQs if none exist
        if (faqRepository.count() == 0) {
            Faq faq1 = new Faq("What is Electiondb?", "Electiondb is a platform for real-time election updates and discussions.");
            Faq faq2 = new Faq("How can I participate?", "Sign up and join threads to discuss various election topics.");
            Faq faq3 = new Faq("Is Electiondb free?", "Yes, Electiondb is free for all users.");
            Faq faq4 = new Faq("How can I report a user?", "Click on the user's profile and select 'Report User'.");
            Faq faq5 = new Faq("How can I become a moderator?", "Contact the admin to apply for a moderator role.");
            Faq faq6 = new Faq("How can I contact support?", "Email support@gmail.com for support inquiries.");

            faqRepository.saveAll(Arrays.asList(faq1, faq2, faq3, faq4, faq5, faq6));
        } else {
            System.out.println("FAQs already exist, skipping FAQ seeding.");
        }

        System.out.println("Users, Threads, ThreadComments, and FAQs seeded successfully if not already present!");
    }
}
