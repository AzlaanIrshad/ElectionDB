package com.example.service;

import com.example.entity.Faq;
import com.example.entity.Thread;
import com.example.entity.ThreadComment;
import com.example.entity.User;
import com.example.repository.FaqRepository;
import com.example.repository.ThreadRepository;
import com.example.repository.ThreadCommentRepository;
import com.example.repository.UserRepository;
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

    @Autowired
    private FaqRepository faqRepository;

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        // Seed Users
        User regularUser = new User("test", "test@test", "test", User.Role.USER);
        User modUser = new User("modUser", "mod@example.com", "modpw", User.Role.MODERATOR);
        User adminUser = new User("adminUser", "admin@example.com", "adminpw", User.Role.ADMIN);

        User[] users = {regularUser, modUser, adminUser};
        for (User user : users) {
            userRepository.save(user);
        }

        // Seed Threads
        Thread thread1 = new Thread("Thread 1", "Body 1", "2021-09-01", "Category 1", regularUser);
        Thread thread2 = new Thread("Thread 2", "Body 2", "2021-09-02", "Category 2", modUser);
        Thread thread3 = new Thread("Thread 3", "Body 3", "2021-09-03", "Category 3", adminUser);

        threadRepository.save(thread1);
        threadRepository.save(thread2);
        threadRepository.save(thread3);

        // Seed Thread Comments
        ThreadComment comment1 = new ThreadComment(regularUser, thread1, "Comment 1", "2021-09-01", "Category 1");
        ThreadComment comment2 = new ThreadComment(modUser, thread2, "Comment 2", "2021-09-02", "Category 2");
        ThreadComment comment3 = new ThreadComment(adminUser, thread3, "Comment 3", "2021-09-03", "Category 3");

        threadCommentRepository.save(comment1);
        threadCommentRepository.save(comment2);
        threadCommentRepository.save(comment3);

        // Seed FAQs
        Faq faq1 = new Faq("What is Electiondb?", "Electiondb is a platform for real-time election updates and discussions.");
        Faq faq2 = new Faq("How can I participate?", "Sign up and join threads to discuss various election topics.");
        Faq faq3 = new Faq("Is Electiondb free?", "Yes, Electiondb is free for all users.");
        Faq faq4 = new Faq("How can I report a user?", "Click on the user's profile and select 'Report User'.");
        Faq faq5 = new Faq("How can I become a moderator?", "Contact the admin to apply for a moderator role.");
        Faq faq6 = new Faq("How can I contact support?", "Email support@gmail.com for support inquiries.");

        faqRepository.save(faq1);
        faqRepository.save(faq2);
        faqRepository.save(faq3);
        faqRepository.save(faq4);
        faqRepository.save(faq5);
        faqRepository.save(faq6);

        System.out.println("Users, Threads, ThreadComments, and FAQs saved successfully!");
    }
}
