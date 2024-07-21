package com.turfnovo.auth.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turfnovo.global.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User extends Auditable {
  public enum UserRole {
    ROLE_ADMIN,
    ROLE_NORMAL_USER,
    ROLE_TURF_OWNER
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First name is mandatory")
  @Size(max = 50, message = "First name should not exceed 50 characters")
  @Column(name = "first_name")
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  @Size(max = 50, message = "Last name should not exceed 50 characters")
  @Column(name = "last_name")
  private String lastName;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Set<UserRole> roles;

  @Column(name = "is_blocked")
  private Boolean isBlocked;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @NotBlank(message = "Username is mandatory")
  @Size(max = 50, message = "Username should not exceed 50 characters")
  @Column(name = "username", unique = true)
  private String username;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, message = "Password should be at least 8 characters long")
  @Column(name = "password")
  @JsonIgnore
  private String password;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email should not exceed 100 characters")
  @Column(name = "email", unique = true)
  private String email;

  @NotBlank(message = "Phone number is mandatory")
  @Size(max = 10, min = 10, message = "Phone number should have 10 digits")
  @Column(name = "phone_no")
  private String phoneNo;

  @PrePersist
  public void assignInitialValues() {
    this.roles = new HashSet<>();
    this.roles.add(UserRole.ROLE_NORMAL_USER);
    this.isBlocked = false;
  }

  public void updateNonNullFields(User user) {
    if (user == null) {
      return;
    }
    if (user.getFirstName() != null) {
      this.firstName = user.getFirstName();
    }
    if (user.getLastName() != null) {
      this.lastName = user.getLastName();
    }
    if (user.getRoles() != null) {
      this.roles = new HashSet<>(user.getRoles());
    }
    if (user.getIsBlocked() != null) {
      this.isBlocked = user.getIsBlocked();
    }
    if (user.getBirthDate() != null) {
      this.birthDate = user.getBirthDate();
    }
    if (user.getUsername() != null) {
      this.username = user.getUsername();
    }
    if (user.getPassword() != null) {
      this.password = user.getPassword();
    }
    if (user.getEmail() != null) {
      this.email = user.getEmail();
    }
    if (user.getPhoneNo() != null) {
      this.phoneNo = user.getPhoneNo();
    }
  }
}
