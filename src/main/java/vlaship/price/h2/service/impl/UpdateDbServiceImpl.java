package vlaship.price.h2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vlaship.price.h2.service.DownloadCsvService;
import vlaship.price.h2.service.UpdateDbService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDbServiceImpl implements UpdateDbService {

    private final DownloadCsvService downloadCsvService;

    @Override
    public void update() {
        log.info("Start updating db");

        final var stringList = downloadCsvService.download();

    }
}
