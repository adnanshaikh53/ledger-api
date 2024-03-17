package org.hsbc.ledgerapi.commons;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class AssetExchangeConstants {
    public static Map<String,Double> assetExchangePair = new HashMap<>();

    public AssetExchangeConstants(){
        assetExchangePair.put("USD^INR",80.0);
        assetExchangePair.put("INR^USD",0.012);
        assetExchangePair.put("USD^ETH",0.5);
        assetExchangePair.put("ETH^USD",2.0);
        assetExchangePair.put("INDIAN_GOVT_BOND^INR",100.0);
        assetExchangePair.put("INR^INDIAN_GOVT_BOND",0.001);
        assetExchangePair.put("INDIAN_GOVT_BOND^USD",1.2);
        assetExchangePair.put("USD^INDIAN_GOVT_BOND",0.012);
    }

    public Optional<Double> getConversionRate(String sourceCurrency, String targetCurrency) {
        if (assetExchangePair.containsKey(sourceCurrency+"^"+targetCurrency)) {
            return Optional.of(assetExchangePair.get(sourceCurrency+"^"+targetCurrency));
        }
        return Optional.empty();
    }

}
