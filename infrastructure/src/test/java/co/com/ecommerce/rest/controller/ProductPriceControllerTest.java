package co.com.ecommerce.rest.controller;

import co.com.ecommerce.rest.model.ProductPriceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@ActiveProfiles("test")
class ProductPriceControllerTest {

    @Autowired
    private RestTestClient webTestClient;

    private final String url = "/api/v1/product-price";

    @Test
    void testGetPriceForProduct_PriceFound10HrsDay14() {
        BigDecimal expectedPrice = BigDecimal.valueOf(35.50).setScale(2, RoundingMode.HALF_UP);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductPriceResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(expectedPrice, response.price());
                });
    }

    @Test
    void testGetPriceForProduct_PriceFound16HrsDay14() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "2020-06-14T16:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductPriceResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(BigDecimal.valueOf(25.45), response.price());
                });
    }

    @Test
    void testGetPriceForProduct_PriceFound21HrsDay14() {
        BigDecimal expectedPrice = BigDecimal.valueOf(35.50).setScale(2, RoundingMode.HALF_UP);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "2020-06-14T21:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductPriceResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(expectedPrice, response.price());
                });
    }

    @Test
    void testGetPriceForProduct_PriceFound10HrsDay15() {
        BigDecimal expectedPrice = BigDecimal.valueOf(30.50).setScale(2, RoundingMode.HALF_UP);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "2020-06-15T10:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductPriceResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(expectedPrice, response.price());
                });
    }

    @Test
    void testGetPriceForProduct_PriceFound21HrsDay16() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "2020-06-16T21:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductPriceResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(BigDecimal.valueOf(38.95), response.price());
                });
    }

    @Test
    void testGetPriceForProduct_PriceNotFound21HrsDay16Ano21() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "2021-06-16T21:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .value(body ->
                        assertTrue(body.contains("\"message\":\"No price found for the given parameters."))
                );
    }

    @Test
    void testGetPriceForProduct_InvalidParameters() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "")
                        .queryParam("productId", "35455a")
                        .queryParam("brandId", "1a")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(body -> {
                    assertTrue(body.contains("\"error\":\"Validation Error\""));
                    assertTrue(body.contains("\"productId\":\"Failed to convert property value of type"));
                    assertTrue(body.contains("\"brandId\":\"Failed to convert property value of type"));
                    assertTrue(body.contains("\"applicationDate\":\"Application Date cannot be null."));
                });
    }

    @Test
    void testGetPriceForProduct_Invalid2Parameters() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("applicationDate", "2021-06-1621:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", "1a")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(body -> {
                    assertTrue(body.contains("\"error\":\"Validation Error\""));
                    assertFalse(body.contains("\"productId\":\"Failed to convert property value of type"));
                    assertTrue(body.contains("\"brandId\":\"Failed to convert property value of type"));
                    assertTrue(body.contains("\"applicationDate\":\"Failed to convert property value of type"));
                });
    }
}
