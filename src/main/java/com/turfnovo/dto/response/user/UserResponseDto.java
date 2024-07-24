package com.turfnovo.dto.response.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.turfnovo.model.orders.Orders;
import com.turfnovo.model.turf.Turf;
import com.turfnovo.model.user.User.UserRole;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;

    private String firstName;

    private String lastName;

    private Set<UserRole> roles;

    private LocalDate birthDate;

    private String email;

    private String phoneNo;

    private List<Orders> orders;

    private List<Turf> turfs;
}
