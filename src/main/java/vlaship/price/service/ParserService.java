package vlaship.price.service;

import vlaship.price.entity.Product;

import java.util.List;

public interface ParserService {
    List<Product> parse(List<String> lines);
}
