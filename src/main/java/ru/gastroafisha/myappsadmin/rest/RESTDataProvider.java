package ru.gastroafisha.myappsadmin.rest;

import java.io.IOException;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alex on 22.12.2017.
 */
public class RESTDataProvider {

    private final IAdminService adminService;

    private String login;
    private String password;

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public RESTDataProvider() {
        //httpClient.interceptors().add(new LoggingInterceptor());
        httpClient.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                if (response.request().header("Authorization") != null) {
                    throw new IOException(response.message());
                }

                String credential = Credentials.basic(login, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        });

        Retrofit retrofit = getRetrofit();
        adminService = retrofit.create(IAdminService.class);
    }

    private final Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public IAdminService getAdminService() {
        return adminService;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
