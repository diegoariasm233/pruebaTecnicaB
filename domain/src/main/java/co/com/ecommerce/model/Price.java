package co.com.ecommerce.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        Long id,
        Brand brand,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priceList,
        Long productId,
        Integer priority,
        BigDecimal price,
        String currency
) {}
