package com.turfnovo.controller.turf;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.turfnovo.ApiPathConstants;
import com.turfnovo.dto.request.turf.TurfRequestDto;
import com.turfnovo.dto.response.turf.TurfResponseDto;
import com.turfnovo.service.turf.TurfService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TurfController {
    private final TurfService turfService;

    @GetMapping(ApiPathConstants.TURF_GET_ALL)
    public List<TurfResponseDto> getTurfController() {
        return turfService.getTurfs();
    }

    @GetMapping(ApiPathConstants.TURF_GET_BY_ID)
    public TurfResponseDto getTurfByIdController(@PathVariable Long id) {
        return turfService.getTurfById(id);
    }

    @PostMapping(ApiPathConstants.TURF_CREATE)
    public TurfResponseDto createTurfController(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody TurfRequestDto turfRequestDto) {
        return turfService.createTurf(authorization, turfRequestDto);
    }

    @PutMapping(ApiPathConstants.TURF_UPDATE)
    public TurfResponseDto putMethodName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable Long id, @RequestBody TurfRequestDto turfRequestDto) {
        return turfService.updateTurf(authorization, id, turfRequestDto);
    }
}
