package co.com.ecommerce.service;

import co.com.ecommerce.external.PriceAdapterInterface;
import co.com.ecommerce.model.Brand;
import co.com.ecommerce.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductPriceServiceTest {

    @Mock
    private PriceAdapterInterface priceAdapterInterface;

    @InjectMocks
    private ProductPriceService priceService;

    @Test
    void testGetPriceForProduct_PriceFound() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 1L;
        Long brandId = 2L;

        Brand brand = new Brand(1L, "ARTURO CALLE");
        Price price = new Price(1L, brand, applicationDate.minusDays(1), applicationDate.plusDays(1),
                1, productId, 1, BigDecimal.valueOf(100.0), "EUR");

        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(price));

        Optional<Price> result = priceService.getPriceForProduct(applicationDate, productId, brandId);

        assertTrue(result.isPresent());
        Price response = result.get();
        assertEquals(price.getProductId(), response.getProductId());
        assertEquals(price.getBrand().getBrandId(), response.getBrand().getBrandId());
        assertEquals(price.getPriceList(), response.getPriceList());
        assertEquals(price.getPrice(), response.getPrice());
        assertEquals(price.getStartDate(), response.getStartDate());
        assertEquals(price.getEndDate(), response.getEndDate());
        verify(priceAdapterInterface).getApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    void testGetPriceForProduct_PriceNotFound() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 1L;
        Long brandId = 2L;

        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.empty());

        Optional<Price> result = priceService.getPriceForProduct(applicationDate, productId, brandId);

        assertFalse(result.isPresent());
        verify(priceAdapterInterface).getApplicablePrice(applicationDate, productId, brandId);
    }
}