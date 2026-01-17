package co.com.ecommerce.rest.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductPriceRequest {

    @NotNull(message = "Product ID cannot be null.")
    private Long productId;

    @NotNull(message = "Brand ID cannot be null.")
    private Long brandId;

    @NotNull(message = "Application Date cannot be null.")
    private LocalDateTime applicationDate;

}
