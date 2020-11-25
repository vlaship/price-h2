package vlaship.price.repository.impl;

import org.springframework.stereotype.Service;
import vlaship.price.entity.Product;
import vlaship.price.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class ProductRepositoryImpl implements ProductRepository {

    private final Map<String, Product> map = new HashMap<>();
    private final ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
    private final ReentrantReadWriteLock.ReadLock readLock = new ReentrantReadWriteLock().readLock();

    @Override
    public List<Product> findByNameProduct(final String searchTerm, final int size) {
        readLock.lock();
        try {
            return map.entrySet().stream()
                    .filter(e -> e.getKey().toLowerCase().contains(searchTerm.toLowerCase()))
                    .limit(size)
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void addAll(final List<Product> list) {
        writeLock.lock();
        try {
            map.clear();
            list.forEach(product -> map.put(product.getNameProduct(), product));
        } finally {
            writeLock.unlock();
        }
    }
}
