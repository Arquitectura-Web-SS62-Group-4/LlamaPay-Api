package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsById(Long id);
    public boolean existsByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT u.client_id FROM users u WHERE u.username = :username AND u.password = :password")
    public Long getIdByUsernameAndPassword(@Param("username") String username, @Param("password") String password );

    @Query(nativeQuery = true, value = "SELECT u.enabled FROM users u WHERE u.username = :username")
    public Boolean getEnabledByUsername(@Param("username") String username);

}
