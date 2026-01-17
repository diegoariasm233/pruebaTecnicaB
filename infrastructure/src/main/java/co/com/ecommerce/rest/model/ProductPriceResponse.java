package co.com.ecommerce.rest.model;

import co.com.ecommerce.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductPriceResponse(Long productId, Long brandId, Integer priceList, LocalDateTime startDate,
                                   LocalDateTime endDate, BigDecimal price) {

    public static ProductPriceResponse from(Price price) {
        return new ProductPriceResponse(
                price.getProductId(),
                price.getBrand().getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice()
        );
    }


}
