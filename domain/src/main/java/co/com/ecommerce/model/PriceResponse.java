package co.com.ecommerce.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public abstract class PriceResponse {

    private final Long productId;
    private final Long brandId;
    private final Integer priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final BigDecimal price;

    protected PriceResponse(Long productId, Long brandId, Integer priceList,
                            LocalDateTime startDate, LocalDateTime endDate, BigDecimal price) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
