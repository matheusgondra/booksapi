package com.matheusgondra.booksapi.infrastructure.security;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final LoadUserByEmailGateway loadUserByEmailGateway;

  public UserDetailsServiceImpl(LoadUserByEmailGateway loadUserByEmailGateway) {
    this.loadUserByEmailGateway = loadUserByEmailGateway;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        this.loadUserByEmailGateway
            .loadByEmail(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + username));
    return new UserDetailsImpl(user);
  }
}
