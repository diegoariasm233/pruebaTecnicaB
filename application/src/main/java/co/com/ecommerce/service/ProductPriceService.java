package co.com.ecommerce.service;

import co.com.ecommerce.external.PriceAdapterInterface;
import co.com.ecommerce.model.Price;
import co.com.ecommerce.model.PriceResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductPriceService {

    private final PriceAdapterInterface priceAdapterInterface;
    private final GeneratePriceResponse generatePriceResponse;

    public ProductPriceService(PriceAdapterInterface priceAdapterInterface,
                               GeneratePriceResponse generatePriceResponse) {
        this.priceAdapterInterface = priceAdapterInterface;
        this.generatePriceResponse = generatePriceResponse;
    }



    public PriceResponse getPriceForProduct(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId) {

        Optional<Price> price = priceAdapterInterface
                .getApplicablePrice(applicationDate, productId, brandId);

        return generatePriceResponse.from(price);
    }

}
