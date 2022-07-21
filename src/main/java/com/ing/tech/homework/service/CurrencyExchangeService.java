package com.ing.tech.homework.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.tech.homework.utils.CurrencyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyExchangeService {
    private ObjectMapper objectMapper;

    @Value("classpath:exchange-rates.json")
    private Resource ratesResource;

    private final Map<CurrencyType, Map<CurrencyType, Double>> exchangeRates;

    public CurrencyExchangeService() {
        exchangeRates = new HashMap<>();
    }

    @PostConstruct
    public void postConstructor() {
        this.objectMapper = new ObjectMapper();
        loadRates();
    }

    private void loadRates() {
        Map<String, Map<String, Double>> readData;
        try {
            readData = this.objectMapper.readValue(ratesResource.getFile(),
                    new TypeReference<Map<String, Map<String, Double>>>() {});

            for (String key : readData.keySet()) {
                Map<String, Double> currentCurrencyRates = readData.get(key);
                CurrencyType type = CurrencyType.valueOf(key);

                exchangeRates.put(type, new HashMap<>());

                for (String currency : currentCurrencyRates.keySet()) {
                    exchangeRates.get(type).put(CurrencyType.valueOf(currency), currentCurrencyRates.get(currency));
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Double getExchangeRate(CurrencyType typeFrom, CurrencyType typeTo) {
        return exchangeRates.get(typeFrom).get(typeTo);
    }
}
