package co.com.ecommerce.rest.controller;

import co.com.ecommerce.rest.exception.ResourceNotFoundException;
import co.com.ecommerce.rest.model.ProductPriceRequest;
import co.com.ecommerce.rest.model.ProductPriceResponse;
import co.com.ecommerce.service.ProductPriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-price")
public class ProductPriceController {

    @Autowired
    private ProductPriceService priceService;

    @GetMapping
    public ProductPriceResponse getProductPrice(
            @Valid @ModelAttribute ProductPriceRequest request) {
        return priceService.getPriceForProduct(
                request.applicationDate(),
                request.productId(),
                request.brandId())
                .map(ProductPriceResponse::from)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No price found for the given parameters.")
                );
    }
}
