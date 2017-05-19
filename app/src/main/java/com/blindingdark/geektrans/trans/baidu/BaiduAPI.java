package com.blindingdark.geektrans.trans.baidu;

import com.blindingdark.geektrans.trans.baidu.bean.BaiduJSONBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by blindingdark on 17-5-19.
 */

public interface BaiduAPI {
    @FormUrlEncoded
    @POST("api/trans/vip/translate")
    Call<BaiduJSONBean> getResult(@Field("q") String query,
                                  @Field("from") String from,
                                  @Field("to") String to,
                                  @Field("appid") String appid,
                                  @Field("salt") long salt,
                                  @Field("sign") String sign);

    @FormUrlEncoded
    @POST("api/trans/vip/translate")
    Call<BaiduJSONBean> getResult(@FieldMap Map<String, String> params);
}
