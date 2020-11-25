package vlaship.price.repository;

import vlaship.price.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findByNameProduct(String searchTerm, int size);

    void addAll(List<Product> list);
}
