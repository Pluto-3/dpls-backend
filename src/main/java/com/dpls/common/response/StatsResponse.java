package com.dpls.common.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class StatsResponse {
    private long totalApplications;
    private Map<String, Long> applicationByStatus;
    private double averageProcessingTimeInDays;
}
