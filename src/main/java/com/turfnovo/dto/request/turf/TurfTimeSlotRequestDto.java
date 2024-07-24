package com.turfnovo.dto.request.turf;

import java.time.LocalDateTime;

import com.turfnovo.model.turf.TurfTimeSlot.TurfTimeSlotStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurfTimeSlotRequestDto {
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
}
