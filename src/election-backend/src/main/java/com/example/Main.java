package main.java.com.example;

import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Create users with appropriate roles
        User regularUser = createUser("gaga", "gaga@example.com", "password123", User.Role.USER);
        User modUser = createUser("modUser", "mod@example.com", "modpw", User.Role.MODERATOR);
        User adminUser = createUser("adminUser", "admin@example.com", "adminpw", User.Role.ADMIN);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save all users
            saveUser(session, regularUser);
            saveUser(session, modUser);
            saveUser(session, adminUser);

            transaction.commit();
            System.out.println("Users saved successfully!");
        } catch (Exception e) {
            System.err.println("An error occurred while saving the users: " + e.getMessage());
        }

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

    private static User createUser(String username, String email, String password, User.Role role) {
        return new User(username, email, password, role);
    }

    private static void saveUser(Session session, User user) {
        session.save(user);
    }
}
