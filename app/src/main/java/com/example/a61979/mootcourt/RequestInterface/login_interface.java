package com.example.a61979.mootcourt.RequestInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public interface login_interface {
//    @GET("token")
//    Call<ResponseBody> getCall(@Query("userid") String userid,@Query("password") String password);
        @GET
        Call<ResponseBody> getCall(@Url String url, @Query("userid") String userid, @Query("password") String password);
}
