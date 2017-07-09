package com.nothing.splider.data;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Data;

import java.util.List;

/**
 * Created by dick on 2017/7/8.
 */
@Data
public class BalanceSheetDetailItem implements HtmlBean {
    @HtmlField(cssPath="td:nth-child(1) > *")
    private String name;

    @HtmlField(cssPath="td[style=text-align:right;]")
    private List<String> values;
}
