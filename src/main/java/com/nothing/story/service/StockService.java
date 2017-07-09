package com.nothing.story.service;

import com.nothing.splider.SpringPipelineFactory;
import com.nothing.story.model.Stock;
import com.nothing.story.repository.StockPropRepository;
import com.nothing.story.repository.StockRepository;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.stereotype.Service;

/**
 * Created by dick on 2017/7/7.
 */
@Service
public class StockService implements CommandMarker {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockPropRepository stockPropRepository;

    @Autowired
    SpringPipelineFactory springPipelineFactory;


//    @CliCommand(value = "sm event", help = "Sends an event to a state machine")
//    public String event(@CliOption(key = { "", "event" }, mandatory = true, help = "The event") final String event) {
//        return "Event " + event + " send";
//    }


    public void fetchBalanceSheet(){
        for(Stock stock: stockRepository.findAll()){
            HttpRequest request = new HttpGetRequest("http://money.finance.sina.com.cn/corp/go.php/vFD_BalanceSheet/stockid/"+stock.getCode().substring(2)+"/ctrl/part/displaytype/4.phtml");
            request.setCharset("GBK");
            GeccoEngine.create()
                    .classpath("com.nothing.splider.data")
                    .pipelineFactory(springPipelineFactory)
                    .interval(2000)
                    //开始抓取的页面地址
                    .start(request)
                    .run();
        }
    }

    public void fetchProfitStatement(){
        for(Stock stock: stockRepository.findAll()){
            HttpRequest request = new HttpGetRequest("http://money.finance.sina.com.cn/corp/go.php/vFD_ProfitStatement/stockid/"+stock.getCode().substring(2)+"/ctrl/part/displaytype/4.phtml");
            request.setCharset("GBK");
            GeccoEngine.create()
                    .classpath("com.nothing.splider.data")
                    .pipelineFactory(springPipelineFactory)
                    .interval(2000)
                    //开始抓取的页面地址
                    .start(request)
                    .run();
        }
    }

    public void fetchCashFlow(){
        for(Stock stock: stockRepository.findAll()){
            HttpRequest request = new HttpGetRequest("http://money.finance.sina.com.cn/corp/go.php/vFD_CashFlow/stockid/"+stock.getCode().substring(2)+"/ctrl/part/displaytype/4.phtml");
            request.setCharset("GBK");
            GeccoEngine.create()
                    .classpath("com.nothing.splider.data")
                    .pipelineFactory(springPipelineFactory)
                    .interval(2000)
                    //开始抓取的页面地址
                    .start(request)
                    .run();
        }
    }
}
