package vlaship.price.h2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vlaship.price.h2.service.DownloadCsv;
import vlaship.price.h2.service.UpdateDb;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDbImpl implements UpdateDb {

    private final DownloadCsv downloadCsv;

    @Override
    public void update() {
        log.info("Start updating db");

        final var stringList = downloadCsv.download();

    }
}
