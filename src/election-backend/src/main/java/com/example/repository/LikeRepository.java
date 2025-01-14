package com.example.repository;

import com.example.entity.Like;
import com.example.entity.Thread;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByThread(Thread thread);

    Optional<Like> findByThreadAndUser(Thread thread, User user);

    List<Like> findByUser(User user);
}
