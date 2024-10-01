package main.java.com.example;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        // Create a new user
        User user = new User("john_doe", "john@example.com", "password123");

        // Open a session and begin a transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        // Save the user
        session.save(user);

        // Commit the transaction and close the session
        transaction.commit();
        session.close();

        System.out.println("User saved successfully!");
    }
}
