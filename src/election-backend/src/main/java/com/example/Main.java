package main.java.com.example;

import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    }

    private static User createUser(String username, String email, String password) {
        return new User(username, email, password);
    }

    private static void saveUser(Session session, User user) {
        session.save(user);
    }
}