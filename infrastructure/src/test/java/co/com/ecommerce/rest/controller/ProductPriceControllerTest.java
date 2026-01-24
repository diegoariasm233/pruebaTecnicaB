package co.com.ecommerce.rest.controller;

import co.com.ecommerce.rest.exception.ErrorResponse;
import co.com.ecommerce.rest.helper.ErrorResponseAssert;
import co.com.ecommerce.rest.model.ProductPriceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ProductPriceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/api/v1/prices";

    private String buildUrl(String applicationDate, long productId, long brandId) {
        return UriComponentsBuilder.fromPath(BASE_URL)
                .queryParam("applicationDate", applicationDate)
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .toUriString();
    }

    @Test
    void testPrice_10HrsDay14() {
        BigDecimal expectedPrice = BigDecimal.valueOf(35.50).setScale(2, RoundingMode.HALF_UP);

        ResponseEntity<ProductPriceResponse> response = restTemplate.getForEntity(
                buildUrl("2020-06-14T10:00:00", 35455, 1),
                ProductPriceResponse.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().price().setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedPrice);
    }

    @Test
    void testPrice_16HrsDay14() {
        BigDecimal expectedPrice = BigDecimal.valueOf(25.45).setScale(2, RoundingMode.HALF_UP);

        ResponseEntity<ProductPriceResponse> response = restTemplate.getForEntity(
                buildUrl("2020-06-14T16:00:00", 35455, 1),
                ProductPriceResponse.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().price().setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedPrice);
    }

    @Test
    void testPrice_21HrsDay14() {
        BigDecimal expectedPrice = BigDecimal.valueOf(35.50).setScale(2, RoundingMode.HALF_UP);

        ResponseEntity<ProductPriceResponse> response = restTemplate.getForEntity(
                buildUrl("2020-06-14T21:00:00", 35455, 1),
                ProductPriceResponse.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().price().setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedPrice);
    }

    @Test
    void testPrice_10HrsDay15() {
        BigDecimal expectedPrice = BigDecimal.valueOf(30.50).setScale(2, RoundingMode.HALF_UP);

        ResponseEntity<ProductPriceResponse> response = restTemplate.getForEntity(
                buildUrl("2020-06-15T10:00:00", 35455, 1),
                ProductPriceResponse.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().price().setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedPrice);
    }

    @Test
    void testPrice_21HrsDay16() {
        BigDecimal expectedPrice = BigDecimal.valueOf(38.95).setScale(2, RoundingMode.HALF_UP);

        ResponseEntity<ProductPriceResponse> response = restTemplate.getForEntity(
                buildUrl("2020-06-16T21:00:00", 35455, 1),
                ProductPriceResponse.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().price().setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedPrice);
    }

    @Test
    void givenMultiplePrices_whenGetPrice_thenReturnHighestPriority() {
        ResponseEntity<ProductPriceResponse> response = restTemplate.getForEntity(
                buildUrl("2020-06-14T16:00:00", 35455, 1),
                ProductPriceResponse.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        ProductPriceResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.price()).isEqualByComparingTo(BigDecimal.valueOf(25.45).setScale(2, RoundingMode.HALF_UP));
        assertThat(body.priceList()).isEqualTo(2);
    }


    @Test
    void givenNonExistingPrice_whenGetPrice_thenReturn404() {
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
                buildUrl("2021-06-16T21:00:00", 35455, 1),
                ErrorResponse.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isNotNull();
        ErrorResponseAssert.assertNotFound(response.getBody(),
                "No price found for the given parameters.");
    }

    @Test
    void givenInvalidParameters_whenGetPrice_thenReturnBadRequest() {
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
                buildUrl("", 35455, 1),
                ErrorResponse.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().fieldErrors()).containsKey("applicationDate");

        ErrorResponseAssert.assertValidationError(response.getBody(),
                "applicationDate");
    }

    @Test
    void givenAllInvalidParameters_whenGetPrice_thenReturnBadRequest() {
        String uri = UriComponentsBuilder.fromPath(BASE_URL)
                .queryParam("applicationDate", "")
                .queryParam("productId", "")
                .queryParam("brandId", "")
                .toUriString();

        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(uri, ErrorResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        ErrorResponseAssert.assertValidationError(response.getBody(),
                "applicationDate", "productId", "brandId");
    }


    @Test
    void givenBoundaryDates_whenGetPrice_thenReturnCorrectPrice() {
         ResponseEntity<ProductPriceResponse> responseStart = restTemplate.getForEntity(
                buildUrl("2020-06-14T15:00:00", 35455, 1),
                ProductPriceResponse.class
        );
        assertThat(responseStart.getBody()).isNotNull();
        assertThat(responseStart.getBody().priceList()).isEqualTo(2);

        ResponseEntity<ProductPriceResponse> responseEnd = restTemplate.getForEntity(
                buildUrl("2020-06-14T18:30:00", 35455, 1),
                ProductPriceResponse.class
        );
        assertThat(responseEnd.getBody()).isNotNull();
        assertThat(responseEnd.getBody().priceList()).isEqualTo(2);
    }
}
