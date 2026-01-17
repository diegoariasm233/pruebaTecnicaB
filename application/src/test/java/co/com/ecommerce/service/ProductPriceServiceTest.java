package co.com.ecommerce.service;

import co.com.ecommerce.external.PriceAdapterInterface;
import co.com.ecommerce.model.Brand;
import co.com.ecommerce.model.DummyPriceResponse;
import co.com.ecommerce.model.Price;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

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

    @BeforeEach
    public void setUp(){
        ReflectionTestUtils.setField(priceService, "generatePriceResponse", new GenerateDummyPriceResponse());
    }

    @Test
    void testGetPriceForProduct_PriceFound() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 1L;
        Long brandId = 2L;

        Brand brand = new Brand(1L, "ZARA");
        Price price = new Price(1L, brand, applicationDate.minusDays(1), applicationDate.plusDays(1),
                1, productId, 1, BigDecimal.valueOf(100.0), "EUR");
        Optional<Price> priceOptional = Optional.of(price);
        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(priceOptional);

        DummyPriceResponse result = (DummyPriceResponse)
                priceService.getPriceForProduct(applicationDate, productId, brandId);

        assertEquals(price.getProductId(), result.getProductId());
        assertEquals(price.getBrand().getBrandId(), result.getBrandId());
        assertEquals(price.getPriceList(), result.getPriceList());
        assertEquals(price.getPrice(), result.getPrice());
        assertEquals(price.getStartDate(), result.getStartDate());
        assertEquals("assert this implementation", result.getAnotherVariableToImplement());
        assertEquals(price.getEndDate(), result.getEndDate());
        verify(priceAdapterInterface).getApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    void testGetPriceForProduct_PriceNotFound() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 1L;
        Long brandId = 2L;

        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> priceService.getPriceForProduct(applicationDate, productId, brandId)
                , "No price found for the given parameters. from test");
    }
}