package co.com.ecommerce.adapters.repository;

import co.com.ecommerce.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
class PriceAdapterImplTest {

    @Autowired
    private PriceAdapterImpl priceAdapterImpl;

    @Test
    void testFindPriceByDateAndProductIdAndBrandId_ReturnsPrice() {
        Optional<Price> result = priceAdapterImpl
                .getApplicablePrice(LocalDateTime
                        .of(2020, 6, 14, 16, 0 , 0),
                        35455L, 1L);
        assertFalse(result.isEmpty());
        assertTrue(result.stream()
                .allMatch(price ->
                        price.productId().equals(35455L) && price.brand().brandId().equals(1L)
                )
        );

    }


    @Test
    void testFindPriceByDateAndProductIdAndBrandId_ReturnsEmpty_WhenNoPriceFound() {
        Optional<Price> result = priceAdapterImpl
                .getApplicablePrice(LocalDateTime
                        .of(2020, 6, 14, 16, 0 , 0),
                        25L, 1L);
        assertTrue(result.isEmpty());
    }
}