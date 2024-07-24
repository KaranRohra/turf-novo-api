package com.turfnovo.dto.request.user;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {

    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @Size(max = 15, message = "Phone number should not exceed 15 characters")
    private String phoneNo;

    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;
}
