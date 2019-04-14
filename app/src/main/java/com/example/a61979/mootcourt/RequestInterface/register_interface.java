package com.example.a61979.mootcourt.RequestInterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public interface register_interface {
   // @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("register")
    Call<ResponseBody> getCall(@Body RequestBody route);

}
