package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    public Authority findByName(String name);
}
