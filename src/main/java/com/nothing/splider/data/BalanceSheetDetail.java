package com.nothing.splider.data;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Data;

import java.util.List;

/**
 * Created by dick on 2017/7/8.
 */
@Gecco(matchUrl="http://money.finance.sina.com.cn/corp/go.php/vFD_{type}/stockid/{code}/ctrl/{year}/displaytype/4.phtml", pipelines="balanceSheetDetailPipeline",downloader="htmlUnitDownloder")
@Data
public class BalanceSheetDetail implements HtmlBean {

    @Request
    private HttpRequest request;

    @RequestParameter
    private String type;

    @RequestParameter
    private String year;

    @RequestParameter
    private String code;

    @HtmlField(cssPath="#toolbar > div.tbtb01 > h1 > a")
    private String name;

    @HtmlField(cssPath="#con02-1 > table:nth-child(2) > tbody > tr ")
    private List<BalanceSheetDetailItem> details;

    @HtmlField(cssPath="#con02-1 > table:nth-child(1) > tbody > tr > td > a")
    @Attr("href")
    private List<String> urls;

}
