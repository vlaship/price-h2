package vlaship.price.h2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vlaship.price.h2.entity.Product;
import vlaship.price.h2.service.ParserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParserServiceImpl implements ParserService {

    @Override
    public List<Product> parse(final List<String> lines) {
        log.info("Start parsing ...");
        return lines.stream()
                .map(this::parse)
                .collect(Collectors.toList());
    }

    private Product parse(final String line) {
        final String[] elements = line.replace("\"", "").split(";");
        return Product.builder()
                .brand(elements[0])
                .category(elements[1])
                .subCategory(elements[2])
                .vendorCode(elements[3])
                .nameProduct(elements[4])
                .price(BigDecimal.valueOf(Double.parseDouble(elements[5])))
                .build();
    }

}
