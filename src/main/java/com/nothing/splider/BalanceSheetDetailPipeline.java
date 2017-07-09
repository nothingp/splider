package com.nothing.splider;

import com.nothing.splider.data.BalanceSheetDetail;
import com.nothing.splider.data.BalanceSheetDetailItem;
import com.nothing.story.model.StockProp;
import com.nothing.story.repository.StockPropRepository;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dick on 2017/7/8.
 */
@PipelineName("balanceSheetDetailPipeline")
@Service
public class BalanceSheetDetailPipeline implements Pipeline<BalanceSheetDetail> {

    @Autowired
    StockPropRepository stockPropRepository;

    @Override
    public void process(BalanceSheetDetail balanceSheetDetail) {
        if("part".equals(balanceSheetDetail.getYear())){
            for(String url:balanceSheetDetail.getUrls()){
                HttpRequest currRequest = balanceSheetDetail.getRequest();
                DeriveSchedulerContext.into(currRequest.subRequest(url));
            }
        }else{
            List<BalanceSheetDetailItem> list = balanceSheetDetail.getDetails();

            for(int i =3;i<list.size();i++){
                BalanceSheetDetailItem item = list.get(i);

                for(int j = 0;j<item.getValues().size();j++){
                    StockProp prop = new StockProp();
                    prop.setCode(balanceSheetDetail.getCode());
                    prop.setName(balanceSheetDetail.getName());
                    prop.setYear(balanceSheetDetail.getYear());
                    prop.setMonth(list.get(0).getValues().get(j));
                    prop.setProp(item.getName());
                    String value = item.getValues().get(j).replace(",","");
                    if("--".equals(value))
                        value = "0.00";
                    prop.setValue(Double.valueOf(value));

                    prop.setType(balanceSheetDetail.getType());

                    stockPropRepository.save(prop);
                }
            }
        }

    }

}

