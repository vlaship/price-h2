package vlaship.price.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private UUID id;
    private String vendorCode;
    private String nameProduct;
    private BigDecimal recommendedPrice;
    private BigDecimal ourPrice;
    private Exist existM;
    private Exist existV;
    private Exist existP;
}

