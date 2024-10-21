package com.example.service;

import com.example.entity.Thread;
import com.example.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository ThreadRepository;

    public Thread createThread(Thread thread) {
        return ThreadRepository.save(thread);
    }

    public List<Thread> getThreads() {
        return ThreadRepository.findAll();
    }

    public Thread getThreadById(Long id) {
        Optional<Thread> optionalThread = ThreadRepository.findById(id);

        if (optionalThread.isPresent()) {
            return optionalThread.get();
        } else {
            throw new RuntimeException("Thread not found with id: " + id);  // Handle appropriately
        }
    }

    public boolean deleteThread(Long id) {
        if (ThreadRepository.existsById(id)) {
            ThreadRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
