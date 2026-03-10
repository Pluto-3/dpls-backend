package com.dpls.permit;

import com.dpls.application.ApplicationRepository;
import com.dpls.common.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PermitExpirationJob {

    private final PermitRepository permitRepository;
    private final ApplicationRepository applicationRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void expirePermits() {
        List<Permit> expiredPermits = permitRepository.findExpiredPermits(LocalDateTime.now());

        for (Permit permit : expiredPermits) {
            permit.getApplication().setStatus(ApplicationStatus.EXPIRED);
            applicationRepository.save(permit.getApplication());
        }

        log.info("Permit expiration job ran. {} permits expired.", expiredPermits.size());
    }
}