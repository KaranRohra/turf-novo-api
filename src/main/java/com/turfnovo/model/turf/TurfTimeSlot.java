package com.turfnovo.model.turf;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turfnovo.model.orders.Orders;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TurfTimeSlot {
    public enum TurfTimeSlotStatus {
        AVAILABLE,
        BOOKED,
        REMOVED,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start Date Time is mandatory")
    private LocalDateTime startDateTime;

    @NotNull(message = "End Date Time is mandatory")
    private LocalDateTime endDateTime;

    @NotNull(message = "Turf Time Slot Status is mandatory")
    @Enumerated(EnumType.STRING)
    private TurfTimeSlotStatus turfTimeSlotStatus;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "turf_id", nullable = false)
    @JsonIgnore
    private Turf turf;

    @OneToMany(mappedBy = "turfTimeSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Orders> orders;
}
