package com.turfnovo.dto.request.turf;

import java.util.List;

import com.turfnovo.model.turf.Turf.TurfStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurfRequestDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;

    @NotNull(message = "Turf Status is mandatory")
    @Enumerated(EnumType.STRING)
    private TurfStatus turfStatus;

    @NotBlank(message = "Address Line 1 is mandatory")
    @Size(max = 255, message = "Address Line 1 cannot be longer than 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address Line 2 cannot be longer than 255 characters")
    private String addressLine2;

    @NotBlank(message = "Pincode is mandatory")
    @Size(max = 6, min = 6, message = "Pincode should be 6 characters")
    private String pincode;

    @NotBlank(message = "City is mandatory")
    @Size(max = 100, message = "City cannot be longer than 100 characters")
    private String city;

    @NotBlank(message = "District is mandatory")
    @Size(max = 100, message = "District cannot be longer than 100 characters")
    private String district;

    @NotBlank(message = "State is mandatory")
    @Size(max = 100, message = "State cannot be longer than 100 characters")
    private String state;

    @NotBlank(message = "Phone Number 1 is mandatory")
    @Size(max = 15, message = "Phone Number 1 cannot be longer than 15 characters")
    private String phoneNo1;

    @Size(max = 15, message = "Phone Number 2 cannot be longer than 15 characters")
    private String phoneNo2;

    @NotNull(message = "Time slots are mandatory")
    private List<TurfTimeSlotRequestDto> turfTimeSlots;
}
