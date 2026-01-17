package co.com.ecommerce.external;

import co.com.ecommerce.model.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceAdapterInterface {
    Optional<Price> getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
