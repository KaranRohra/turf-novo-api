package com.turfnovo.dto.request.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {
  @NotBlank(message = "First name is mandatory")
  @Size(max = 50, message = "First name should not exceed 50 characters")
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  @Size(max = 50, message = "Last name should not exceed 50 characters")
  private String lastName;

  private LocalDate birthDate;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, message = "Password should be at least 8 characters long")
  private String password;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email should not exceed 100 characters")
  private String email;

  @NotBlank(message = "Phone number is mandatory")
  @Size(max = 10, min = 10, message = "Phone number should have 10 digits")
  private String phoneNo;

}
