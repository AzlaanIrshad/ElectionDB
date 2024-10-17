package com.example;

import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        // Create User
        User user = createUser("gaga", "gaga@example.com", "password123");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save User
            saveUser(session, user);

            transaction.commit();
            System.out.println("User saved successfully!");
        } catch (Exception e) {
            System.err.println("An error occurred while saving the user: " + e.getMessage());
        }

        // Keep the app running
        try {
            while (true) {
                TimeUnit.HOURS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.err.println("Persistent process interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    private static User createUser(String username, String email, String password) {
        return new User(username, email, password);
    }

    private static void saveUser(Session session, User user) {
        session.save(user);
    }
}
