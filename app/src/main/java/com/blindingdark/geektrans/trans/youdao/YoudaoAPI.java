package com.blindingdark.geektrans.trans.youdao;

import com.blindingdark.geektrans.trans.youdao.bean.YoudaoJSONBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by blindingdark on 17-5-20.
 */

public interface YoudaoAPI {
    @GET("openapi.do?type=data&doctype=json&version=1.2")
    Call<YoudaoJSONBean> getResult(@Query("q") String query,
                                   @Query("key") String key,
                                   @Query("keyfrom") String keyfrom);
}
