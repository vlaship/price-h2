package vlaship.price.h2.service;

import vlaship.price.h2.entity.Product;

import java.util.List;

public interface UploadDataService {
    void upload(List<Product> products);
}
