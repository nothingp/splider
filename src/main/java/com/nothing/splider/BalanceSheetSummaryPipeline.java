package com.nothing.splider;

import com.nothing.splider.data.BalanceSheetSummary;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import org.springframework.stereotype.Service;

/**
 * Created by dick on 2017/7/8.
 */
@PipelineName("balanceSheetSummaryPipeline")
@Service
public class BalanceSheetSummaryPipeline implements Pipeline<BalanceSheetSummary> {

    @Override
    public void process(BalanceSheetSummary balanceSheetSummary) {
        for(String url:balanceSheetSummary.getUrls()){
            HttpRequest currRequest = balanceSheetSummary.getRequest();
            DeriveSchedulerContext.into(currRequest.subRequest(url));
        }
    }

}

