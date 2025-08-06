package com.matheusgondra.booksapi.infrastructure.repository;

import com.matheusgondra.booksapi.infrastructure.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByEmail(String email);
}
