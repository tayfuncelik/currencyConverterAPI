package com.example.demo.service;

import com.example.demo.dto.CurrencyApiResponse;
import com.example.demo.dto.CurrencyDto;
import com.example.demo.dto.ExchangedDto;
import com.example.demo.model.CurrencyEnum;
import com.example.demo.model.CurrencyModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"currency"})
public class CurrencyService implements Serializable {

    private static final long serialVersionUID = 1L;
    Logger logger = LoggerFactory.getLogger(getClass());


    @Value( "${api.config.endPoint}" )
    private String endPoint;//base=TRY  symbols=USD,TRY


    @Autowired
    private CurrencyApiResponse currencyApiResponse;

    @Cacheable()
    public List<CurrencyEnum> getCurrencyList() {
        List<CurrencyEnum> currencyEnumList = Arrays.asList(CurrencyEnum.values());
        logger.info("Currency enum values fetching..");
        return currencyEnumList;
    }


    public ExchangedDto getExchangeCurrency(CurrencyDto currencyDto) {
        CurrencyModel currencyModel = convertDTOtoEntity(currencyDto);// hiding model with DTO pattern

        CurrencyApiResponse currencyResponse = connectAndFetchData(currencyModel);

        Double target = Double.valueOf(currencyResponse.getRates().get(currencyModel.getTargetCurrency().getDisplayName()));
        Double exchangedValue = currencyModel.getMonetary() * target;

        ExchangedDto exchangedDto = new ExchangedDto();
        exchangedDto.setExchangedValue(exchangedValue);
        exchangedDto.setMonetary(currencyDto.getValue());

        logger.info("{} {} is {} {}", exchangedDto.getMonetary(), currencyModel.getSourceCurrency(), exchangedDto.getExchangedValue(), currencyModel.getTargetCurrency());
        return exchangedDto;
    }

    private CurrencyApiResponse connectAndFetchData(CurrencyModel currencyModel) {

        CurrencyApiResponse currencyApiResponse = null;

        URL url = null;
        HttpURLConnection request = null;
        try {
            String prefix = "base=".concat(currencyModel.getSourceCurrency().getDisplayName());
            url = new URL(endPoint + prefix);//"USD,GBP"
            request = (HttpURLConnection) url.openConnection();
            request.connect();
        } catch (MalformedURLException e) {
            logger.error("URL error {}", e);
        } catch (IOException ex) {
            logger.error("IO error {}", ex);
        } catch (Exception ext) {
            logger.error("HTTP call error {}", ext);
        }

        JsonObject jsonobj = null;
        try {
            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            jsonobj = root.getAsJsonObject();
        } catch (IOException e) {
            logger.error("conveting to json error {}", e);
        } catch (Exception ex) {
            logger.error("Json parse error {}", ex);
        }

        ObjectMapper m = new ObjectMapper();

        try {
            currencyApiResponse = m.readValue(jsonobj.toString(), CurrencyApiResponse.class);
        } catch (JsonProcessingException e) {
            logger.error("converting JSON to Class Error {}", e);
        }

        return currencyApiResponse;
    }


    private CurrencyModel convertDTOtoEntity(CurrencyDto currencyDto) {

        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setMonetary(currencyDto.getValue());
        currencyModel.setSourceCurrency(currencyDto.getSource());
        currencyModel.setTargetCurrency(currencyDto.getTarget());
        return currencyModel;
    }


}
