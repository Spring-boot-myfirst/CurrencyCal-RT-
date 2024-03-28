package com.Currency.Calc.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Currency.Calc.bean.CurrencyCalcBean;

@Service
public class CurrencyCalcService {

    public CurrencyCalcBean getConvertedDetails(String from, String to, BigDecimal quantity) {
        // Set up URI variables
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        
        // Call currency exchange service using RestTemplate
        ResponseEntity<CurrencyCalcBean> responseEntity = new RestTemplate().getForEntity(
            "http://localhost:8001/currency-exchange/from/{from}/to/{to}",
            CurrencyCalcBean.class,
            uriVariables
        );
        
        // Get response from exchange service
        CurrencyCalcBean response = responseEntity.getBody();
        
        // Create new response bean
        return new CurrencyCalcBean(
            response.getId(),
            from,
            to,
            quantity.multiply(response.getConversionRate()),
            response.getConversionRate()
        );
    }
}
