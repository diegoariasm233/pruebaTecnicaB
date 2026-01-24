package co.com.ecommerce.service;

import co.com.ecommerce.exception.PriceNotFoundException;
import co.com.ecommerce.external.PriceAdapterInterface;
import co.com.ecommerce.model.Brand;
import co.com.ecommerce.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductPriceServiceTest {

    @Mock
    private PriceAdapterInterface priceAdapterInterface;

    @InjectMocks
    private ProductPriceService priceService;

    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
    private Brand brand;
    private Price price;

    @BeforeEach
    void setUp() {
        applicationDate = LocalDateTime.now();
        productId = 1L;
        brandId = 2L;
        brand = new Brand(1L, "ARTURO");
        price = new Price(1L, brand,
                applicationDate.minusDays(1),
                applicationDate.plusDays(1),
                1,
                productId,
                1,
                BigDecimal.valueOf(100.0),
                "EUR");
    }

    @Test
    void givenExistingPrice_whenGetPrice_thenReturnPrice() {
        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(price));

        Price result = priceService.getPriceForProduct(applicationDate, productId, brandId);
        assertThat(result).usingRecursiveComparison().isEqualTo(price);

        verify(priceAdapterInterface).getApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    void givenNoPrice_whenGetPrice_thenThrowPriceNotFoundException() {
        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () ->
                priceService.getPriceForProduct(applicationDate, productId, brandId));

        verify(priceAdapterInterface).getApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    void givenMultiplePricesWithDifferentPriorities_whenGetPrice_thenReturnCorrectOne() {
        Price higherPriorityPrice = new Price(2L, brand,
                applicationDate.minusHours(1),
                applicationDate.plusHours(1),
                2,
                productId,
                2,
                BigDecimal.valueOf(150.0),
                "EUR");
        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(higherPriorityPrice));

        Price result = priceService.getPriceForProduct(applicationDate, productId, brandId);

        assertThat(result.priority()).isEqualTo(2);
        assertThat(result.price()).isEqualByComparingTo(BigDecimal.valueOf(150.0));
    }

    @Test
    void givenPriceWithDifferentCurrency_whenGetPrice_thenReturnPriceWithCurrency() {
        Price usdPrice = new Price(3L, brand,
                applicationDate.minusHours(1),
                applicationDate.plusHours(1),
                1,
                productId,
                1,
                BigDecimal.valueOf(120.0),
                "USD");
        when(priceAdapterInterface.getApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(usdPrice));
        Price result = priceService.getPriceForProduct(applicationDate, productId, brandId);
        assertThat(result.currency()).isEqualTo("USD");
        assertThat(result.price()).isEqualByComparingTo(BigDecimal.valueOf(120.0));
    }
}
