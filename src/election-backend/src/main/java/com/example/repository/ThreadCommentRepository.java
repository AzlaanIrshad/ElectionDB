package com.example.repository;

import com.example.entity.ThreadComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThreadCommentRepository extends JpaRepository<ThreadComment, Long> {
    List<ThreadComment> findAll();
    Optional<ThreadComment> findById(Long id);
    ThreadComment save(ThreadComment threadcomment);
    List<ThreadComment> findByThreadId(Long threadId);
    ThreadComment findByUserId(Long userId);
}