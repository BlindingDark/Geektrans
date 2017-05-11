package com.blindingdark.geektrans.trans.jinshan;

import com.blindingdark.geektrans.trans.jinshan.bean.JinshanJSONBean;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanJSONBeanZh;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by blindingdark on 17-5-10.
 */

public interface JinshanAPI {
    @GET("api/dictionary.php?type=json")
    Call<JinshanJSONBean> getResult(@Query("w") String word, @Query("key") String key);

    @GET("api/dictionary.php?type=json")
    Call<JinshanJSONBeanZh> getZhResult(@Query("w") String word, @Query("key") String key);
}
