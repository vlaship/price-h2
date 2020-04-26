package vlaship.price.h2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vlaship.price.h2.entity.Product;
import vlaship.price.h2.service.ParserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParserServiceImpl implements ParserService {

    @Value("${price.our-discount}")
    private int discount;

    @Override
    public List<Product> parse(final List<String> lines) {
        log.info("Start parsing...");
        return lines.stream()
                .skip(1)
                .map(this::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Product parse(final String line) {
        final var elements = line.replace("\"", "").split(";");
        final var price = BigDecimal.valueOf(Double.parseDouble(elements[5]));
        return Product.builder()
                .brand(elements[0])
                .category(elements[1])
                .subCategory(elements[2])
                .vendorCode(elements[3])
                .nameProduct(elements[4])
                .recommendedPrice(price)
                .ourPrice(price.multiply(BigDecimal.valueOf(100 - discount)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP))
                .build();
    }

}
