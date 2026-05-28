package com.example.shardedSagaWallet.repository;

import com.example.shardedSagaWallet.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNameContainingIgnoreCase(String name);
}
