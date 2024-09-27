package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);

    public User findByUsername(String username);
}
