package vlaship.price.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlaship.price.h2.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameProductContainingIgnoreCase(String searchTerm);
}
