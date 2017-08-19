package com.nothing.story.service;

import com.nothing.splider.SpringPipelineFactory;
import com.nothing.story.model.Stock;
import com.nothing.story.model.StockPropBalance;
import com.nothing.story.model.StockPropLabel;
import com.nothing.story.model.StockPropQuery;
import com.nothing.story.repository.StockPropBalanceRepository;
import com.nothing.story.repository.StockPropLabelRepository;
import com.nothing.story.repository.StockRepository;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * Created by dick on 2017/7/7.
 */
@Service
public class StockService implements CommandMarker {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockPropBalanceRepository stockPropBalanceRepository;

    @Autowired
    StockPropLabelRepository stockPropLabelRepository;

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

    public Collection<StockPropQuery> report(String code, String[] props,Integer[] time){
        Map<String,StockPropQuery> result = new LinkedHashMap<>();
        List<StockPropBalance> list  = stockPropBalanceRepository.findByCodeAndPropInAndTimeBetweenOrderByMonth(code, Arrays.asList(props),time[0],time[1]);

        list.forEach(stockPropBalance->{
            StockPropQuery stockPropQuery = result.get(stockPropBalance.getCode()+stockPropBalance.getMonth());
            if(stockPropQuery==null){
                stockPropQuery = new StockPropQuery();
                stockPropQuery.setCode(stockPropBalance.getCode());
                stockPropQuery.setName(stockPropBalance.getName());
                stockPropQuery.setYear(stockPropBalance.getYear());
                stockPropQuery.setMonth(stockPropBalance.getMonth());
                result.put(stockPropBalance.getCode()+stockPropBalance.getMonth(),stockPropQuery);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(stockPropBalance.getMonth());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR,-1);

            List<StockPropBalance> yoyList = stockPropBalanceRepository.findByCodeAndMonthAndProp(stockPropBalance.getCode(),sdf.format(calendar.getTime()),stockPropBalance.getProp());
            if(yoyList.size()>0) {
                BigDecimal cal = new BigDecimal(yoyList.get(0).getValue());
                cal = cal.subtract(new BigDecimal(stockPropBalance.getValue()));
                cal = cal.multiply(new BigDecimal(100));
                if (stockPropBalance.getValue().intValue() == 0) {
                    stockPropQuery.getItems().put(stockPropBalance.getProp()+"yoy", new Double(1));
                } else {
                    cal = cal.divide(new BigDecimal(stockPropBalance.getValue()).abs(), 2, ROUND_HALF_DOWN);
                    stockPropQuery.getItems().put(stockPropBalance.getProp()+"yoy", cal.doubleValue());
                }
            }

            calendar.setTime(date);
            calendar.add(Calendar.MONTH,-3);

            List<StockPropBalance> momList = stockPropBalanceRepository.findByCodeAndMonthAndProp(stockPropBalance.getCode(),sdf.format(calendar.getTime()),stockPropBalance.getProp());
            if(momList.size()>0){
                BigDecimal cal = new BigDecimal(momList.get(0).getValue());
                cal = cal.subtract(new BigDecimal(stockPropBalance.getValue()));
                cal = cal.multiply(new BigDecimal(100));
                if(stockPropBalance.getValue().intValue()==0){
                    stockPropQuery.getItems().put(stockPropBalance.getProp()+"mom",new Double(1));
                }else{
                    cal = cal.divide(new BigDecimal(stockPropBalance.getValue()).abs(),2,ROUND_HALF_DOWN);
                    stockPropQuery.getItems().put(stockPropBalance.getProp()+"mom",cal.doubleValue());
                }
            }

            stockPropQuery.getItems().put(stockPropBalance.getProp(),stockPropBalance.getValue());
        });


        return result.values();
    }

    public List<StockPropLabel> findTree(){
        List<StockPropLabel> list = stockPropLabelRepository.findByParentId("-1");
        return list;
    }

    public List<Stock> search(String code){
        return stockRepository.findByCodeLike(code);
    }
}
