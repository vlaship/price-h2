package vlaship.price.h2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vlaship.price.h2.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Long, Product> {
    Page<Product> findAllByNameProductIsLike(String filter, Pageable pageable);

    List<Product> findAllByNameProductIsLike(String filter);
}
