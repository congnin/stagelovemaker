package jp.stage.stagelovemaker.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jp.stage.stagelovemaker.MyApplication;
import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.model.SignUpModel;
import jp.stage.stagelovemaker.utils.Utils;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by congn on 8/11/2017.
 */

public class NetworkManager {
    private static String BASE_API = "http://153.126.150.57/love-maker/";
    private static Retrofit retrofit = null;
    MyApiEndpoint apiService;
    private Context mContext;
    private IHttpResponse iHttpResponse;
    private ProgressDialog progressDialog;

    public NetworkManager(Context context, IHttpResponse iHttpResponse) {
        try {
            this.iHttpResponse = iHttpResponse;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "IHttpResponse");
        }
        this.mContext = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apiService = getClient((Activity) context).create(MyApiEndpoint.class);
    }

    public static Retrofit getClient(final Activity activity) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    MyApplication app = Utils.getApplication(activity);
                    String accessToken = "";
                    if (app != null) {
                        accessToken = app.getAccessToken(activity);
                        if (TextUtils.isEmpty(accessToken)) {
                            accessToken = "";
                        }
                    }
                    String formatedToken = "Bearer " + accessToken;
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Authorization", formatedToken)
                            .addHeader("Content-type", "application/x-www-form-urlencoded");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public void requestApi(final Call<ResponseModel> call, final int idRequest) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (call == null) {
                    return;
                }
                progressDialog.show();
                progressDialog.setContentView(R.layout.dialog_progress_bar);
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (mContext instanceof Activity) {
                            if (!((Activity) mContext).isFinishing()) {
                                if ((progressDialog != null) && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                            ResponseModel responseModel = response.body();
                            if (responseModel != null) {
                                if (responseModel.getStatus() == 200) {
                                    iHttpResponse.onHttpComplete(responseModel.getResult().toString(), idRequest);
                                } else {
                                    iHttpResponse.onHttpError(responseModel.getResult().toString(), idRequest, responseModel.getStatus());
                                }
                            } else {
                                Toast.makeText(mContext, mContext.getString(R.string.unknown_error_network), Toast.LENGTH_LONG).show();
                                iHttpResponse.onHttpError("", idRequest, 0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        if (mContext instanceof Activity) {
                            if (!((Activity) mContext).isFinishing())
                                if ((progressDialog != null) && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                        }
                        Toast.makeText(mContext, mContext.getString(R.string.unknown_error_network), Toast.LENGTH_LONG).show();
                        iHttpResponse.onHttpError("", idRequest, 0);
                    }
                });
            }
        }, 150);
    }

    public void requestApiNoProgress(final Call<ResponseModel> call, final int idRequest) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (call == null) {
                    return;
                }
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        ResponseModel responseModel = response.body();
                        if (responseModel != null) {
                            if (responseModel.getStatus() == 200) {
                                iHttpResponse.onHttpComplete(responseModel.getResult().toString(), idRequest);
                            } else {
                                iHttpResponse.onHttpError(responseModel.getResult().toString(), idRequest, responseModel.getStatus());
                            }
                        } else {
                            iHttpResponse.onHttpError(mContext.getString(R.string.unknown_error_network), idRequest, 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(mContext, mContext.getString(R.string.unknown_error_network), Toast.LENGTH_LONG).show();
                        iHttpResponse.onHttpError("", idRequest, 0);
                    }
                });
            }
        }, 150);
    }

    public Call<ResponseModel> signUp(SignUpModel signUpModel) {
        JsonObject data = new JsonObject();
        data.addProperty("email", signUpModel.getEmail());
        data.addProperty("username", signUpModel.getUsername());
        data.addProperty("password", signUpModel.getPassword());
        data.addProperty("first_name", signUpModel.getFirst_name());
        data.addProperty("last_name", signUpModel.getLast_name());
        data.addProperty("birthday", signUpModel.getBirthday());
        data.addProperty("gender", signUpModel.getGender());

        return apiService.signUp(data);
    }

    public Call<ResponseModel> signIn(String username, String password) {
        JsonObject data = new JsonObject();
        data.addProperty("username", username);
        data.addProperty("password", password);
        return apiService.signIn(data);
    }

    public Call<ResponseModel> validateUsernameAndEmail(String username, String email) {
        //String usernameAndEmail = String.format("username=%s&email=%s", username, email);
        return apiService.validateUserAndEmail(username, email);
    }

    public Call<ResponseModel> uploadAvatar(int index, Bitmap bitmap) {
        File newfile = Utils.savebitmap(mContext, bitmap, "avatar");
        newfile.getPath();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/png"), newfile);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("avatar", newfile.getName(), requestFile);
        return apiService.uploadAvatar(body, index);
    }

    public Call<ResponseModel> getProfile(int id) {
        return apiService.getProfile(id);
    }

    public Call<ResponseModel> updateLocation(int id, double latitude, double longitude) {
        JsonObject data = new JsonObject();
        data.addProperty("latitude", latitude);
        data.addProperty("longitude", longitude);

        return apiService.updateLocation(id, data);
    }

    public Call<ResponseModel> forgotPassword(String email) {
        JsonObject data = new JsonObject();
        data.addProperty("email", email);
        return apiService.forgotPassword(data);
    }

    public Call<ResponseModel> updatePassword(String email, String code_number, String password) {
        JsonObject data = new JsonObject();
        data.addProperty("email", email);
        data.addProperty("code_number", code_number);
        data.addProperty("password", password);
        return apiService.updatePassword(data);
    }
}
