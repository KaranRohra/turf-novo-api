package com.turfnovo.model.orders;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turfnovo.model.Auditable;
import com.turfnovo.model.turf.TurfTimeSlot;
import com.turfnovo.model.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
public class Orders extends Auditable {
    public enum OrderStatus {
        BOOKED,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start Date Time is mandatory")
    private LocalDateTime startDateTime;

    @NotNull(message = "End Date Time is mandatory")
    private LocalDateTime endDateTime;

    @NotNull(message = "Order Status is mandatory")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "turf_time_slot_id", nullable = false)
    @JsonIgnore
    private TurfTimeSlot turfTimeSlot;
}
