package co.com.ecommerce.rest.controller;

import co.com.ecommerce.model.PriceResponse;
import co.com.ecommerce.rest.model.ProductPriceRequest;
import co.com.ecommerce.service.ProductPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-price")
@RequiredArgsConstructor
public class ProductPriceController {

    private final ProductPriceService priceService;

    @GetMapping
    public PriceResponse getProductPrice(
            @Valid @ModelAttribute ProductPriceRequest request) {

        return priceService.getPriceForProduct(
                request.getApplicationDate(),
                request.getProductId(),
                request.getBrandId()
        );
    }
}
