package co.com.ecommerce.adapters.repository;

import co.com.ecommerce.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PriceAdapterImplTest {

    @Autowired
    private PriceAdapterImpl priceAdapterImpl;


    @Test
    void testFindPriceByDateProductBrand_ReturnsPrice() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 14, 16, 0),
                35455L, 1L
        );

        assertThat(result).isPresent();
        Price price = result.get();
        assertThat(price.productId()).isEqualTo(35455L);
        assertThat(price.brand().brandId()).isEqualTo(1L);
        assertThat(price.price()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
    }

    @Test
    void testFindPriceByDateProductBrand_ReturnsEmpty_WhenNoPrice() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 14, 16, 0),
                25L, 1L
        );
        assertThat(result).isEmpty();
    }

    @Test
    void testFindPriceByDateProductBrand_ReturnsEmpty_WhenBrandDoesNotExist() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 14, 16, 0),
                35455L, 99L
        );
        assertThat(result).isEmpty();
    }

    @Test
    void testFindPriceAtStartDateOfRange() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 14, 0, 0),
                35455L, 1L
        );

        assertThat(result).isPresent();
        assertThat(result.get().price()).isEqualByComparingTo(BigDecimal.valueOf(35.50));
    }

    @Test
    void testFindPriceAtEndDateOfRange() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 14, 18, 30),
                35455L, 1L
        );

        assertThat(result).isPresent();
        assertThat(result.get().price()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
    }


    @Test
    void testFindPriceWithMultipleMatches_ReturnsHighestPriority() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 14, 16, 0),
                35455L, 1L
        );

        assertThat(result).isPresent();
        Price price = result.get();
        assertThat(price.priceList()).isEqualTo(2);
        assertThat(price.price()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
    }

    @Test
    void testFindPriceBeforeAllRanges_ReturnsEmpty() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2019, 12, 31, 23, 59),
                35455L, 1L
        );

        assertThat(result).isEmpty();
    }

    @Test
    void testFindPriceAfterAllRanges_ReturnsEmpty() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2021, 1, 1, 0, 0),
                35455L, 1L
        );

        assertThat(result).isEmpty();
    }

    @Test
    void testFindPrice_10AM_14June() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 14, 10, 0),
                35455L, 1L
        );

        assertThat(result).isPresent();
        assertThat(result.get().price()).isEqualByComparingTo(BigDecimal.valueOf(35.50));
    }

    @Test
    void testFindPrice_21PM_16June() {
        Optional<Price> result = priceAdapterImpl.getApplicablePrice(
                LocalDateTime.of(2020, 6, 16, 21, 0),
                35455L, 1L
        );

        assertThat(result).isPresent();
        assertThat(result.get().price()).isEqualByComparingTo(BigDecimal.valueOf(38.95));
    }
}
