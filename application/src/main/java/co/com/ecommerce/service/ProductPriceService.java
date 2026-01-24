package co.com.ecommerce.service;

import co.com.ecommerce.exception.PriceNotFoundException;
import co.com.ecommerce.external.PriceAdapterInterface;
import co.com.ecommerce.model.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductPriceService {

    private final PriceAdapterInterface priceAdapterInterface;

    public ProductPriceService(PriceAdapterInterface priceAdapterInterface) {
        this.priceAdapterInterface = priceAdapterInterface;
    }

    public Price getPriceForProduct(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId) {
        return priceAdapterInterface
                .getApplicablePrice(applicationDate, productId, brandId).orElseThrow(
                        () -> new PriceNotFoundException("No price found for the given parameters.")
                );
    }

}
