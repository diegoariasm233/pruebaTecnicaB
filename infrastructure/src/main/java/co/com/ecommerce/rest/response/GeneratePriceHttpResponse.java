package co.com.ecommerce.rest.response;

import co.com.ecommerce.model.PriceResponse;
import co.com.ecommerce.rest.exception.ResourceNotFoundException;
import co.com.ecommerce.rest.model.ProductPriceResponse;
import co.com.ecommerce.service.GeneratePriceResponse;
import co.com.ecommerce.model.Price;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneratePriceHttpResponse extends GeneratePriceResponse {

    @Override
    protected PriceResponse from(Optional<Price> price) {
        if(price.isEmpty()){
            throw new ResourceNotFoundException("No price found for the given parameters.");
        }
        return ProductPriceResponse.from(price.get());
    }

}
