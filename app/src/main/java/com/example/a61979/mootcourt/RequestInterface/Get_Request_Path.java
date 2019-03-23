package com.example.a61979.mootcourt.RequestInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public interface Get_Request_Path {
    //@GET("{path}")
    @GET
    Call<ResponseBody> getCall(@Url String url);
}
