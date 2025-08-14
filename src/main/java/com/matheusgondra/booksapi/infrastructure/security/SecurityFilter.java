package com.matheusgondra.booksapi.infrastructure.security;

import com.matheusgondra.booksapi.application.protocol.cryptography.TokenDecode;
import com.matheusgondra.booksapi.infrastructure.enums.PublicRoutes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenDecode tokenDecode;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String route = request.getRequestURI();

    boolean isPublicRoute =
        Arrays.stream(PublicRoutes.values()).anyMatch(r -> r.getRoute().equals(route));
    boolean isSwaggerRoute =
        route.startsWith("/api/docs")
            || route.startsWith("/api/v3/api-docs")
            || route.startsWith("/api/swagger-ui");
    if (isPublicRoute || isSwaggerRoute) {
      filterChain.doFilter(request, response);
      return;
    }

    String header = request.getHeader("Authorization");
    boolean isInvalidHeader = header == null || !header.startsWith("Bearer ");
    if (isInvalidHeader) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String token = header.substring("Bearer ".length());
    String subject = this.tokenDecode.decode(token);
    if (subject == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    UsernamePasswordAuthenticationToken authtoken =
        new UsernamePasswordAuthenticationToken(subject, null, null);
    authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authtoken);

    filterChain.doFilter(request, response);
  }
}
