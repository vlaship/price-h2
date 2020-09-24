package vlaship.price.h2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void upload(List<Product> products) {
        log.info("deleting data ...");
        productRepository.deleteAll();
        productRepository.flush();
        log.info("start uploading ...");
        productRepository.saveAll(products);
        log.info("uploading has been completed");
    }
}
