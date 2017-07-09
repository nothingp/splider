package com.nothing.story.controller;

import com.nothing.story.service.StockService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/api/gupiao", method = RequestMethod.GET)
public class GupiaoController {


    @Autowired
    StockService stockService;

    @RequestMapping(value = "fetchBalanceSheet")
    @ResponseBody
    @SneakyThrows
    public HttpEntity<Void> fetchBalanceSheet() {
        stockService.fetchBalanceSheet();

        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "fetchCashFlow")
    @ResponseBody
    @SneakyThrows
    public HttpEntity<Void> fetchCashFlow() {
        stockService.fetchCashFlow();

        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "fetchProfitStatement")
    @ResponseBody
    @SneakyThrows
    public HttpEntity<Void> fetchProfitStatement() {
        stockService.fetchProfitStatement();

        return ResponseEntity.accepted().build();
    }

}