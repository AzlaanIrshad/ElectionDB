package com.example.repository;

import com.example.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    Optional<Party> findByName(String name);

}
