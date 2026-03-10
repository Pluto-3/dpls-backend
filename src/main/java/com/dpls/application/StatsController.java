package com.dpls.application;

import com.dpls.common.response.ApiResponse;
import com.dpls.common.response.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<StatsResponse>> getStats() {
        StatsResponse response = statsService.getStats();
        return ResponseEntity.ok(ApiResponse.success("Statistics retrieved", response));
    }
}