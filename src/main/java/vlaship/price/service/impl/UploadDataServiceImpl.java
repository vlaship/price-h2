package vlaship.price.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vlaship.price.entity.Product;
import vlaship.price.repository.ProductRepository;
import vlaship.price.service.UploadDataService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadDataServiceImpl implements UploadDataService {

    private final ProductRepository productRepository;

    @Override
    public void upload(final List<Product> products) {
        log.info("start uploading ...");
        productRepository.addAll(products);
        log.info("uploading has been completed");
    }

}
