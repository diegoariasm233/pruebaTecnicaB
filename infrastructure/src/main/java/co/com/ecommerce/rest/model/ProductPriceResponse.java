package co.com.ecommerce.rest.model;

import co.com.ecommerce.model.Price;
import co.com.ecommerce.model.PriceResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductPriceResponse extends PriceResponse {


    private final String currency;

    public ProductPriceResponse(Long productId, Long brandId, Integer priceList, LocalDateTime startDate,
                                LocalDateTime endDate, BigDecimal price, String currency) {
        super(productId, brandId, priceList, startDate, endDate, price);
        this.currency = currency;
    }

    public static ProductPriceResponse from(Price price) {
        return new ProductPriceResponse(
                price.getProductId(),
                price.getBrand().getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurrency()
        );
    }


}
