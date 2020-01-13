package com.example.demo;

import com.example.demo.dto.CurrencyDto;
import com.example.demo.dto.ExchangedDto;
import com.example.demo.model.CurrencyEnum;
import com.example.demo.service.CurrencyService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * https://api.exchangeratesapi.io/latest?base=TRY
 * https://api.exchangeratesapi.io/latest?symbols=USD,GBP
 */
@Timed()
@RestController
@RequestMapping("/api")
public class CurrencyController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CurrencyService currencyService;

    /**
     * this method will be cached because currency will not change in time
     *
     * @return
     */


    @GetMapping("/getCurrencyList")
    private ResponseEntity<List<CurrencyEnum>> getCurrencyList() {
        return ResponseEntity.ok(currencyService.getCurrencyList());
    }

    /***
     * Input will be source , target currency and value
     * @param currencyDto
     * @return
     */
    @Timed("hello.api.time")
    @PostMapping("/getExchangeCurrency")
    public ResponseEntity<ExchangedDto> getExchangeCurrency(
            @ApiParam(value = "currency params", required = true) @RequestBody CurrencyDto currencyDto
    ) {
        RegistryConfig.helloApiCounter.increment();
        if (!isInputsValid(currencyDto)) {
            return new ResponseEntity<>(new ExchangedDto(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(currencyService.getExchangeCurrency(currencyDto));
    }

    private Boolean isInputsValid(CurrencyDto currencyDto) {

        if (currencyDto.getSource() == null || currencyDto.getTarget() == null || currencyDto.getValue() == null) {
            return false;
        }
        return true;
    }
}