package com.example.demo;

import com.example.demo.dto.CurrencyDto;
import com.example.demo.dto.ExchangedDto;
import com.example.demo.model.CurrencyEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecuredControllerSpringBootIntegrationTest {

    @Test
    void contextLoads() {
    }

    @LocalServerPort
    private int port;

    private static String host = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${adminUser}")
    private String adminUser;

    @Value("${adminPassword}")
    private String adminPassword;

    @Test
    public void getCurrency() {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setSource(CurrencyEnum.USD);
        currencyDto.setTarget(CurrencyEnum.TRY);
        currencyDto.setValue(Double.valueOf(30));
        ResponseEntity<ExchangedDto> responseEntity = this.restTemplate
                .withBasicAuth(adminUser, adminPassword)
                .postForEntity(host + port + "/api/getExchangeCurrency", currencyDto, ExchangedDto.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotEquals(null, responseEntity.getBody().getExchangedValue());
    }

    @Test
    public void missingOrEmptyInputs() {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setSource(CurrencyEnum.USD);
        currencyDto.setTarget(CurrencyEnum.TRY);
        currencyDto.setValue(null);
        ResponseEntity<ExchangedDto> responseEntity = this.restTemplate
                .withBasicAuth(adminUser, adminPassword)
                .postForEntity(host + port + "/api/getExchangeCurrency", currencyDto, ExchangedDto.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals(null, responseEntity.getBody().getExchangedValue());
    }

    /**
     * this method is missing /api  path
     */
    @Test
    public void corsCheck() {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setSource(CurrencyEnum.USD);
        currencyDto.setTarget(CurrencyEnum.TRY);
        currencyDto.setValue(Double.valueOf(30));
        ResponseEntity<ExchangedDto> responseEntity = this.restTemplate
                .withBasicAuth(adminUser, adminPassword)
                .postForEntity(host + port + "/getExchangeCurrency", currencyDto, ExchangedDto.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(null, responseEntity.getBody().getExchangedValue());
    }

    /**
     * this method will check wrong username /password
     */
    @Test
    public void invalidCridentials() {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setSource(CurrencyEnum.USD);
        currencyDto.setTarget(CurrencyEnum.TRY);
        currencyDto.setValue(null);
        ResponseEntity<ExchangedDto> responseEntity = this.restTemplate
                .withBasicAuth(adminUser, adminPassword)
                .postForEntity(host + port + "/getExchangeCurrency", currencyDto, ExchangedDto.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(null, responseEntity.getBody().getExchangedValue());
    }
}
