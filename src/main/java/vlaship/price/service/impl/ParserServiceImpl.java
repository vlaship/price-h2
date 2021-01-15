package vlaship.price.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vlaship.price.entity.Exist;
import vlaship.price.entity.Product;
import vlaship.price.service.ParserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParserServiceImpl implements ParserService {

    @Value("${price.our-discount}")
    private long discount;

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
        try {
            final var elements = line
                    .replace("\"", "")
                    .split(";");
            final var price = parsePrice(elements[4].replaceAll("[A-Z]+", ""));
            return Product.builder()
                    .id(UUID.randomUUID())
                    .vendorCode(elements[2])
                    .nameProduct(elements[3])
                    .recommendedPrice(price)
                    .ourPrice(buildOurPrice(price))
                    .existM(Exist.get(elements[5]))
                    .existV(Exist.get(elements[6]))
                    .existP(Exist.get(elements[7]))
                    .build();
        } catch (Exception ex) {
            log.error("[{}] - error: {}", line, ex.getMessage());
            return null;
        }
    }

    private BigDecimal parsePrice(String element) {
        return StringUtils.isNotBlank(element) ? BigDecimal.valueOf(Double.parseDouble(element)) : BigDecimal.ZERO;
    }

    private BigDecimal buildOurPrice(final BigDecimal price) {
        return price
                .multiply(BigDecimal.valueOf(100 - discount))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
