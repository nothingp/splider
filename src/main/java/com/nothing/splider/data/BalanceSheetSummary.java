package com.nothing.splider.data;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Data;

import java.util.List;

/**
 * Created by dick on 2017/7/8.
 */
@Data
public class BalanceSheetSummary implements HtmlBean {

    @RequestParameter
    private String type;

    @Request
    private HttpRequest request;

    @HtmlField(cssPath="#con02-1 > table:nth-child(1) > tbody > tr > td > a")
    private List<String> urls;


    public static void main(String[] args) throws Exception {
            HttpRequest request = new HttpGetRequest("http://money.finance.sina.com.cn/corp/go.php/vFD_CashFlow/stockid/601166/ctrl/part/displaytype/4.phtml");
        request.setCharset("GBK");
        GeccoEngine.create()
                .classpath("com.nothing.splider")
                //开始抓取的页面地址
                .start(request)
                //开启几个爬虫线程
                .thread(1)
                .run();
    }
}
