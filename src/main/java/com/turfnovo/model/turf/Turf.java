package com.turfnovo.model.turf;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turfnovo.model.Auditable;
import com.turfnovo.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Turf extends Auditable {

    public enum TurfStatus {
        OPEN,
        PERMANENT_CLOSED,
        CLOSED_TODAY,
        IN_RENOVATION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "turf", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TurfTimeSlot> turfTimeSlots;

    @OneToMany(mappedBy = "turf", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RatingAndReview> ratingAndReviews;
}