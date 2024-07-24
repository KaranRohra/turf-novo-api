package com.turfnovo.dto.response.turf;

import java.util.List;

import com.turfnovo.model.turf.RatingAndReview;
import com.turfnovo.model.turf.Turf.TurfStatus;
import com.turfnovo.model.turf.TurfTimeSlot;
import com.turfnovo.model.user.User;

import lombok.Data;

@Data
public class TurfResponseDto {
    private Long id;

    private String name;

    private TurfStatus turfStatus;

    private String addressLine1;

    private String addressLine2;

    private String pincode;

    private String city;

    private String district;

    private String state;

    private String phoneNo1;

    private String phoneNo2;

    private User owner;

    private List<TurfTimeSlot> turfTimeSlots;

    private List<RatingAndReview> ratingAndReviews;
}
