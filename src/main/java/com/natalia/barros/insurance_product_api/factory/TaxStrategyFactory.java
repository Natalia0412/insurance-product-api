package com.natalia.barros.insurance_product_api.factory;

import com.natalia.barros.insurance_product_api.domain.Category;
import com.natalia.barros.insurance_product_api.strategy.*;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class TaxStrategyFactory {

    private final Map<Category, TaxStrategy> strategies = new EnumMap<>(Category.class);

    public TaxStrategyFactory() {
        strategies.put(Category.VIDA, new VidaTaxStrategy());
        strategies.put(Category.AUTO, new AutoTaxStrategy());
        strategies.put(Category.VIAGEM, new ViagemTaxStrategy());
        strategies.put(Category.RESIDENCIAL, new ResidencialTaxStrategy());
        strategies.put(Category.PATRIMONIAL, new PatrimonialTaxStrategy());
    }

    public TaxStrategy getStrategy(Category category) {
        return strategies.get(category);
    }
}
