package vlaship.price.service;

import vlaship.price.entity.Product;

import java.util.List;

public interface UploadDataService {
    void upload(List<Product> products);
}
