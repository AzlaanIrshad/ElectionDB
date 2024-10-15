package main.java.com.example;

import entity.User;
import entity.Candidate;
import entity.ElectionResult;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Create User
        User user = createUser("gaga", "gaga@example.com", "password123");

        // Create Candidate
        Candidate candidate = new Candidate("John Doe", "Party X");

        // Create ElectionResult
        ElectionResult electionResult = new ElectionResult(candidate, 500);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save entities
            saveUser(session, user);
            saveCandidate(session, candidate);   // Save the Candidate
            saveElectionResult(session, electionResult);  // Save the ElectionResult

            transaction.commit();
            System.out.println("User, Candidate, and ElectionResult saved successfully!");
        } catch (Exception e) {
            System.err.println("An error occurred while saving the entities: " + e.getMessage());
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

    private static void saveCandidate(Session session, Candidate candidate) {
        session.save(candidate);
    }

    private static void saveElectionResult(Session session, ElectionResult electionResult) {
        session.save(electionResult);
    }
}