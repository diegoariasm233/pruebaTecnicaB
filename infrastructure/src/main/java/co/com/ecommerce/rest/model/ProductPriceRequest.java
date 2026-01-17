package co.com.ecommerce.rest.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ProductPriceRequest(
        @NotNull(message = "Product ID cannot be null.")
        Long productId,

        @NotNull(message = "Brand ID cannot be null.")
        Long brandId,

        @NotNull(message = "Application Date cannot be null.")
        LocalDateTime applicationDate
) {}