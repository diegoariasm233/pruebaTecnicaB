package co.com.ecommerce.service;

import co.com.ecommerce.model.DummyPriceResponse;
import co.com.ecommerce.model.Price;
import co.com.ecommerce.model.PriceResponse;

import java.util.Optional;

public class GenerateDummyPriceResponse extends GeneratePriceResponse {

    @Override
    protected PriceResponse from(Optional<Price> price) {
        if(price.isEmpty()){
            throw new NullPointerException("No price found for the given parameters. from test");
        }
        return DummyPriceResponse.from(price.get());
    }

}
