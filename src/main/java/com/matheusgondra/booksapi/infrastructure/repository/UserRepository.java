package com.matheusgondra.booksapi.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusgondra.booksapi.infrastructure.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> { 
    Optional<UserEntity> findByEmail(String email);
}
