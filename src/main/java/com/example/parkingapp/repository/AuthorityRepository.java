package com.example.parkingapp.repository;

import com.example.parkingapp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for Authority entity
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
