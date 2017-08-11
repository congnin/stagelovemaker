package jp.stage.stagelovemaker.network;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by congn on 8/11/2017.
 */

public interface MyApiEndpoint {
    @POST("/api/systems/signIn/")
    Call<ResponseModel> signIn(@Body JsonObject data);

    @POST("/api/systems/signUp/")
    Call<ResponseModel> signUp(@Body JsonObject data);

    @GET("/api/systems/validate/")
    Call<ResponseModel> validateUserAndEmail(@Query("field") String username_email);
}
