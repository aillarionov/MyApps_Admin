package ru.gastroafisha.myappsadmin.rest;

import io.reactivex.Single;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.gastroafisha.myappsadmin.model.CityProxy;
import ru.gastroafisha.myappsadmin.model.OrgProxy;
import ru.gastroafisha.myappsadmin.model.SimpleOrgProxy;

public interface IAdminService {

    @GET("admin/cities")
    Single<List<CityProxy>> getCitiesList();

    @GET("admin/orgs")
    Single<List<SimpleOrgProxy>> getOrgsList();

    @GET("admin/org/{id}")
    Single<OrgProxy> getOrg(@Path("id") int id);

    @PUT("admin/org")
    Single<OrgProxy> newOrg(@Body OrgProxy org);

    @POST("admin/org")
    Single<OrgProxy> updateOrg(@Body OrgProxy org);
}
