package com.example.repository;

import com.example.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    List<Thread> findAll();
    Optional<Thread> findById(Long id);
    Thread save(Thread thread);
}