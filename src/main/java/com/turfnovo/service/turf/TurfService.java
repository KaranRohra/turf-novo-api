package com.turfnovo.service.turf;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.turfnovo.dto.request.turf.TurfRequestDto;
import com.turfnovo.dto.request.turf.TurfTimeSlotRequestDto;
import com.turfnovo.dto.response.turf.TurfResponseDto;
import com.turfnovo.model.turf.Turf;
import com.turfnovo.model.turf.TurfTimeSlot;
import com.turfnovo.model.user.User;
import com.turfnovo.repository.turf.TurfRepository;
import com.turfnovo.repository.turf.TurfTimeSlotRepository;
import com.turfnovo.repository.user.UserRepository;
import com.turfnovo.security.JwtUtils;
import com.turfnovo.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TurfService {
    private final TurfRepository turfRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final TurfTimeSlotRepository turfTimeSlotRepository;

    public List<TurfResponseDto> getTurfs() {
        return turfRepository.findAll().stream().map(t -> modelMapper.map(t, TurfResponseDto.class)).toList();
    }

    public TurfResponseDto createTurf(String token, TurfRequestDto turfRequestDto) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User user = userRepository.findByUsername(email).orElse(null);
        Turf turf = modelMapper.map(turfRequestDto, Turf.class);

        turf.setOwner(user);
        turf.setTurfTimeSlots(null);
        Turf savedTurf = turfRepository.save(turf);
        savedTurf.setTurfTimeSlots(createOrUpdateTurfTimeSlots(savedTurf.getId(), turfRequestDto.getTurfTimeSlots()));

        return modelMapper.map(savedTurf, TurfResponseDto.class);
    }

    public TurfResponseDto updateTurf(String token, Long turfId, TurfRequestDto turfRequestDto) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User user = userRepository.findByUsername(email).orElse(null);
        Turf turf = turfRepository.findById(turfId).orElse(null);

        if (turf == null || turf.getOwner().getId() != user.getId()) {
            throw new RuntimeException("Only turf owner can update the turf details");
        }

        for (TurfTimeSlotRequestDto turfTimeSlotRequestDto : turfRequestDto.getTurfTimeSlots()) {
            Long timeSlotId = turfTimeSlotRequestDto.getId();
            TurfTimeSlot turfTimeSlot = timeSlotId == null ? null
                    : turfTimeSlotRepository.findById(timeSlotId).orElse(null);

            if (turfTimeSlot == null) {
                turfTimeSlot = new TurfTimeSlot();
                turfTimeSlot.setTurf(turf);
            }
            Utils.copyNonNullProperties(turfTimeSlotRequestDto, turfTimeSlot);
            turfTimeSlotRepository.save(turfTimeSlot);
        }

        turf = turfRepository.findById(turfId).orElse(null);
        turfRequestDto.setTurfTimeSlots(null);
        Utils.copyNonNullProperties(turfRequestDto, turf);

        return modelMapper.map(turfRepository.save(turf), TurfResponseDto.class);
    }

    public List<TurfTimeSlot> createOrUpdateTurfTimeSlots(Long turfId, List<TurfTimeSlotRequestDto> turfTimeSlots) {
        Turf turf = turfRepository.findById(turfId).orElse(null);

        List<TurfTimeSlot> timeSlots = new ArrayList<>();
        for (TurfTimeSlotRequestDto ts : turfTimeSlots) {
            TurfTimeSlot turfTimeSlot = ts.getId() == null ? null
                    : turfTimeSlotRepository.findById(ts.getId()).orElse(null);

            if (turfTimeSlot == null) {
                turfTimeSlot = new TurfTimeSlot();
            }

            modelMapper.map(ts, turfTimeSlot);
            turfTimeSlot.setTurf(turf);

            timeSlots.add(turfTimeSlot);
        }

        return timeSlots.stream().map(ts -> {
            if (ts.getId() != null) {
                return ts;
            }
            return turfTimeSlotRepository.save(ts);
        }).toList();
    }
}
