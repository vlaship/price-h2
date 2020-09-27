package vlaship.price.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Product {

    @Id
    private ObjectId id;
    private String brand;
    private String category;
    private String vendorCode;
    private String nameProduct;
    private BigDecimal recommendedPrice;
    private BigDecimal ourPrice;
    private Exist existM;
    private Exist existV;
    private Exist existP;
}

