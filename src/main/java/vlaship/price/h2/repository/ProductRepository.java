package vlaship.price.h2.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import vlaship.price.h2.entity.Product;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    Page<Product> findByNameProductContainingIgnoreCase(String searchTerm, Pageable pageable);
}
