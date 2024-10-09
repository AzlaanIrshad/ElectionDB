package main.java.com.example;

import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        User user = createUser("gaga", "gaga@example.com", "password123");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            saveUser(session, user);
            transaction.commit();
            System.out.println("User saved successfully!");
        } catch (Exception e) {
            System.err.println("An error occurred while saving the user: " + e.getMessage());
        }
        // This is temp code to keep the container running for now

        // Keep the application running by adding a persistent process
        try {
            while (true) {
                // Sleep for a long period to simulate persistence
                TimeUnit.HOURS.sleep(1);  // Sleeps for 1 hour, repeat indefinitely
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
