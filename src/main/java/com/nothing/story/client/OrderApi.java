package com.nothing.story.client;

import com.nothing.retrofit.annotation.RetrofitClient;
import com.nothing.story.client.model.DataValue;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dick on 2017/5/31.
 */
@RetrofitClient
public interface OrderApi {

    @POST("/api/mng/moneyorder/get")
    Call<DataValue> getMoneyOrder(@Query("__user_classify") String userClassify, @Query("moneyOrderId") String moneyOrderId);
}
