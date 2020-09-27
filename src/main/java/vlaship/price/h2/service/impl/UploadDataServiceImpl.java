package vlaship.price.h2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlaship.price.h2.entity.Product;
import vlaship.price.h2.repository.ProductRepository;
import vlaship.price.h2.service.UploadDataService;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UploadDataServiceImpl implements UploadDataService {

    private final ProductRepository productRepository;

    @Value("${product.save-at-once}")
    private int countProductSavesAtOnce;

    @Override
    public void upload(final List<Product> products) {
        log.info("deleting data ...");
        productRepository.deleteAll();
        productRepository.flush();
        log.info("start uploading ...");
        saveAll(products);
        log.info("uploading has been completed");
    }

    private void saveAll(final List<Product> products) {
        final var parts = products.size() / countProductSavesAtOnce;
        final var mod = products.size() % countProductSavesAtOnce;

        for (int i = 0; i < parts; i++) {
            final var startIndex = i * countProductSavesAtOnce;
            final var endIndex = startIndex + countProductSavesAtOnce;
            final var subList = products.subList(startIndex, endIndex);
            productRepository.saveAll(subList);
            productRepository.flush();
        }

        if (mod > 0) {
            final var startIndex = parts * countProductSavesAtOnce;
            final var endIndex = startIndex + mod;
            final var subList = products.subList(startIndex, endIndex);
            productRepository.saveAll(subList);
        }
    }
}
