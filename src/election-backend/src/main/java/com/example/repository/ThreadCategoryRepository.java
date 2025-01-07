package com.example.repository;

import com.example.entity.ThreadCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ThreadCategoryRepository extends JpaRepository<ThreadCategory, Long> {
    Optional<ThreadCategory> findByName(String name);
}

