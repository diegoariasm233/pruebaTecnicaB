package co.com.ecommerce.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class DummyPriceResponse extends PriceResponse {

    private final String anotherVariableToImplement;

    protected DummyPriceResponse(Long productId, Long brandId, Integer priceList,
                                 LocalDateTime startDate, LocalDateTime endDate, BigDecimal price,
                                 String anotherVariableToImplement) {
        super(productId, brandId, priceList, startDate, endDate, price);
        this.anotherVariableToImplement = anotherVariableToImplement;
    }

    public static DummyPriceResponse from(Price price) {
        return new DummyPriceResponse(
                price.getProductId(),
                price.getBrand().getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                "assert this implementation"
        );
    }
}
