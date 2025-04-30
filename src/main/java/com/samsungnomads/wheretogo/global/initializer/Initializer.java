package com.samsungnomads.wheretogo.global.initializer;

import com.samsungnomads.wheretogo.domain.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer implements ApplicationRunner {
    private final StationRepository stationRepository;
    private final StationDataInitializer stationDataInitializer;

    @Override
    public void run(ApplicationArguments args) {
        if (stationRepository.count() > 0) {
            return;
        }

        int maxTries = 5;
        int tries = 0;
        boolean success = false;

        while (!success && tries < maxTries) {
            tries++;
            log.info("지하철 정보 초기화 시도 {} 회차", tries);
            success = stationDataInitializer.initialize();
        }

        if (!success) {
            log.error("지하철 정보 초기화 실패. {} 회차 시도 끝", tries);
            return;
        }

        log.info("지하철 정보 초기화 성공. {} 회차 시도 완료", tries);

    }


}
