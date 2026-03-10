package com.natalia.barros.insurance_product_api.factory;

import com.natalia.barros.insurance_product_api.domain.Category;
import com.natalia.barros.insurance_product_api.strategy.*;
import org.springframework.stereotype.Component;

@Component
public class TaxStrategyFactory {
    public TaxStrategy getStrategy(Category category) {

        return switch (category) {

            case VIDA -> new VidaTaxStrategy();
            case AUTO -> new AutoTaxStrategy();
            case VIAGEM -> new ViagemTaxStrategy();
            case RESIDENCIAL -> new ResidencialTaxStrategy();
            case PATRIMONIAL -> new PatrimonialTaxStrategy();
        };
    }
}
