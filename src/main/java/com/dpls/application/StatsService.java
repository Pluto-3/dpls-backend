package com.dpls.application;

import com.dpls.common.enums.ApplicationStatus;
import com.dpls.common.response.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final ApplicationRepository applicationRepository;

    public StatsResponse getStats() {
        long total = applicationRepository.count();

        List<Object[]> statusCounts = applicationRepository.countByStatus();
        Map<String, Long> byStatus = new HashMap<>();
        for (Object[] row : statusCounts) {
            byStatus.put(row[0].toString(), (Long) row[1]);
        }

        double averageProcessingDays = applicationRepository
                .findByStatus(ApplicationStatus.APPROVED)
                .stream()
                .mapToLong(app -> ChronoUnit.DAYS.between(
                        app.getSubmittedAt(), app.getUpdatedAt()))
                .average()
                .orElse(0.0);

        return StatsResponse.builder()
                .totalApplications(total)
                .applicationByStatus(byStatus)
                .averageProcessingTimeInDays(averageProcessingDays)
                .build();
    }
}