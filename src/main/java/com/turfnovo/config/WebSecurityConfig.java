package com.turfnovo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.turfnovo.ApiPathConstants;
import com.turfnovo.model.user.User.UserRole;
import com.turfnovo.security.AuthEntryPointJwt;
import com.turfnovo.security.AuthTokenFilter;
import com.turfnovo.service.user.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(ApiPathConstants.SIGN_IN, ApiPathConstants.SIGN_UP,
                ApiPathConstants.SWAGGER_HTML,
                ApiPathConstants.SWAGGER_WEB_JARS,
                ApiPathConstants.SWAGGER_UI,
                ApiPathConstants.SWAGGER_API_DOCS_CONFIG,
                ApiPathConstants.SWAGGER_API_DOCS)
            .permitAll()
            .requestMatchers(ApiPathConstants.TURF_OWNER + "/**")
            .hasAnyAuthority(UserRole.ROLE_TURF_OWNER.name(), UserRole.ROLE_ADMIN.name())
            .anyRequest().authenticated());

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // @Bean
  // CorsConfigurationSource corsConfigurationSource() {
  // CorsConfiguration configuration = new CorsConfiguration();

  // configuration.setAllowedOrigins(List.of("http://localhost:3000"));
  // configuration.setAllowedMethods(List.of("GET", "POST"));
  // configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

  // UrlBasedCorsConfigurationSource source = new
  // UrlBasedCorsConfigurationSource();

  // source.registerCorsConfiguration("/**", configuration);

  // return source;
  // }
}
