package co.com.ecommerce.adapters.repository;

import co.com.ecommerce.adapters.entities.PriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository extends CrudRepository<PriceEntity, Long> {

    Optional<PriceEntity> findTopByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrand_IdOrderByPriorityDesc(
            LocalDateTime applicationDateForStart,
            LocalDateTime applicationDateForEnd,
            Long productId,
            Long brandId
    );
}
