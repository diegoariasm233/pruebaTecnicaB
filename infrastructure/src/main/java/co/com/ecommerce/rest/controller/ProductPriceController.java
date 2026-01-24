package co.com.ecommerce.rest.controller;

import co.com.ecommerce.rest.model.ProductPriceRequest;
import co.com.ecommerce.rest.model.ProductPriceResponse;
import co.com.ecommerce.service.ProductPriceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prices")
public class ProductPriceController {

    private final ProductPriceService priceService;

    public ProductPriceController(ProductPriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public ProductPriceResponse getProductPrice(
            @Valid @ModelAttribute ProductPriceRequest request) {
        return ProductPriceResponse.from(
                priceService.getPriceForProduct(
                        request.applicationDate(),
                        request.productId(),
                        request.brandId()
                )
        );
    }
}
