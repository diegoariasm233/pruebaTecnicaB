package co.com.ecommerce.service;

import co.com.ecommerce.model.Price;
import co.com.ecommerce.model.PriceResponse;

import java.util.Optional;

public abstract class GeneratePriceResponse {
    protected abstract PriceResponse from(Optional<Price> price);
}
