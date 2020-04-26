package vlaship.price.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brand;
    private String category;
    private String subCategory;
    private String vendorCode;
    private String nameProduct;
    private BigDecimal recommendedPrice;
    private BigDecimal ourPrice;
}

