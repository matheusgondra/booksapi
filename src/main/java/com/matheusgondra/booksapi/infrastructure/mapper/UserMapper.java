package com.matheusgondra.booksapi.infrastructure.mapper;

import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.infrastructure.entity.UserEntity;

public class UserMapper {
	public static User toDomain(UserEntity entity) {
		return new User(
				entity.getId(),
				entity.getFirstName(),
				entity.getLastName(),
				entity.getEmail(),
				entity.getPassword(),
				entity.getCreatedAt(),
				entity.getUpdatedAt());
	}
}
