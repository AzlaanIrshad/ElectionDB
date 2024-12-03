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
            regularUser = new User("gebruiker", "test@test.nl", "$2a$10$wboelo8zLJjg9ZGbqcavm.v5BbCAf6sYpRN5.WyR3txhFo5UnMa7a", Role.USER);
            modUser = new User("moderator", "moderator@test.nl", "$2a$10$GxsRveGw0cW3UXtQdRmf1.Qzn/rkzcUGoq6g13tW2vVeWNZHuZ5ii", Role.MODERATOR);
            adminUser = new User("beheerder", "beheerder@test.nl", "$2a$10$VUQXHMqh7s9uWuQUoMk.LONFk0IZO5CltVL8r8G0s5JIARBtzQOH6", Role.ADMIN);
            // Password van User: Test123
            // Password van Moderator: Mod123
            // Password van Admin: Admin123

            // Save all users in one batch operation
            userRepository.saveAll(Arrays.asList(regularUser, modUser, adminUser));
        } else {
            // Retrieve existing users from the database
            regularUser = userRepository.findByUsername("gebruiker").orElseThrow();
            modUser = userRepository.findByUsername("moderator").orElseThrow();
            adminUser = userRepository.findByUsername("beheerder").orElseThrow();
        }

        // Seed Threads if none exist
        if (threadRepository.count() == 0) {
            Thread thread1 = new Thread("Discussie over Verkiezingen 2024", "Wat is jouw mening over de uitslag?", "2024-11-20", "Politiek", regularUser);
            Thread thread2 = new Thread("Klimaatbeleid en Toekomst", "Hoe belangrijk is klimaatverandering voor jou?", "2024-11-18", "Klimaat", modUser);
            Thread thread3 = new Thread("Economie en Belastingen", "Wat vind je van de huidige belastingtarieven?", "2024-11-17", "Economie", adminUser);
            Thread thread4 = new Thread("Onderwijs in Nederland", "Discussieer over de uitdagingen in het onderwijs", "2024-11-19", "Onderwijs", regularUser);

            threadRepository.saveAll(Arrays.asList(thread1, thread2, thread3, thread4));
        } else {
            System.out.println("Threads bestaan al, overslaan.");
        }

        // Seed Thread Comments if none exist
        if (threadCommentRepository.count() == 0) {
            Thread thread1 = threadRepository.findByTitle("Discussie over Verkiezingen 2024").orElseThrow();
            Thread thread2 = threadRepository.findByTitle("Klimaatbeleid en Toekomst").orElseThrow();
            Thread thread3 = threadRepository.findByTitle("Economie en Belastingen").orElseThrow();

            ThreadComment comment1 = new ThreadComment(regularUser, thread1, "Ik vond de verkiezingen eerlijk verlopen.", "2024-11-20", "Politiek");
            ThreadComment comment2 = new ThreadComment(modUser, thread2, "Klimaatverandering moet de hoogste prioriteit krijgen!", "2024-11-18", "Klimaat");
            ThreadComment comment3 = new ThreadComment(adminUser, thread3, "Belastingverlaging is noodzakelijk voor de middenklasse.", "2024-11-17", "Economie");
            ThreadComment comment4 = new ThreadComment(regularUser, thread1, "Ik denk dat er betere alternatieven waren.", "2024-11-21", "Politiek");

            threadCommentRepository.saveAll(Arrays.asList(comment1, comment2, comment3, comment4));
        } else {
            System.out.println("Thread Reacties bestaan al, overslaan.");
        }

        // Seed FAQs if none exist
        if (faqRepository.count() == 0) {
            Faq faq1 = new Faq("Wat is VerkiezingsDB?", "VerkiezingsDB is een platform voor real-time verkiezingsupdates en discussies.");
            Faq faq2 = new Faq("Hoe kan ik deelnemen?", "Registreer je en doe mee aan discussies over verschillende verkiezingsthema's.");
            Faq faq3 = new Faq("Is VerkiezingsDB gratis?", "Ja, VerkiezingsDB is gratis voor alle gebruikers.");
            Faq faq4 = new Faq("Hoe rapporteer ik een gebruiker?", "Klik op het profiel van de gebruiker en selecteer 'Rapporteer Gebruiker'.");
            Faq faq5 = new Faq("Hoe kan ik moderator worden?", "Neem contact op met de beheerder om te solliciteren als moderator.");
            Faq faq6 = new Faq("Hoe neem ik contact op met de support?", "Stuur een e-mail naar support@verkiezingsdb.nl voor hulp.");

            faqRepository.saveAll(Arrays.asList(faq1, faq2, faq3, faq4, faq5, faq6));
        } else {
            System.out.println("FAQs bestaan al, overslaan.");
        }

        System.out.println("Gebruikers, Threads, Reacties en FAQs succesvol gezaaid als ze niet al bestonden!");
    }
}
