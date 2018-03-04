package com.example.parkingapp.repository;

import com.example.parkingapp.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);
}
