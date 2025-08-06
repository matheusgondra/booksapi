package com.matheusgondra.booksapi.infrastructure.gateway;

import com.matheusgondra.booksapi.application.protocol.gateway.AddUserGateway;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.infrastructure.entity.UserEntity;
import com.matheusgondra.booksapi.infrastructure.mapper.UserMapper;
import com.matheusgondra.booksapi.infrastructure.repository.UserRepository;
import java.util.Optional;

public class UserGateway implements LoadUserByEmailGateway, AddUserGateway {
  private final UserRepository userRepository;

  public UserGateway(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> loadByEmail(String email) {
    return userRepository.findByEmail(email).map(UserMapper::toDomain);
  }

  @Override
  public User add(User user) {
    UserEntity entity = this.userRepository.save(UserMapper.toEntity(user));
    return UserMapper.toDomain(entity);
  }
}
