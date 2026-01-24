package co.com.ecommerce.adapters.repository;

import co.com.ecommerce.adapters.entities.PriceEntity;
import co.com.ecommerce.external.PriceAdapterInterface;
import co.com.ecommerce.model.Price;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceAdapterImpl implements PriceAdapterInterface {

    private final PriceRepository priceRepository;

    public PriceAdapterImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    @Override
    public Optional<Price> getApplicablePrice(LocalDateTime applicationDate,
                                                                            Long productId,
                                                                            Long brandId) {
        return priceRepository
                .findTopByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrand_IdOrderByPriorityDesc(applicationDate,
                        applicationDate, productId, brandId).map(PriceEntity::toDomainModel);
    }
}
