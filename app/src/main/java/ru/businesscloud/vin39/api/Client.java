package ru.businesscloud.vin39.api;

import java.util.ArrayList;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import ru.businesscloud.vin39.models.Auth;
import ru.businesscloud.vin39.models.Cars;
import ru.businesscloud.vin39.models.Model;
import ru.businesscloud.vin39.models.Profil;
import ru.businesscloud.vin39.models.Save;

public interface Client {

    @GET("profile/")
    Call<Profil> getProfile(@Query("sid") String sid);

    @GET("car/")
    Call<ArrayList<Cars>> getCars();

    @GET("my/car/")
    Call<ArrayList<Cars>> getMyCars(@Query("sid") String sid);

    @GET("archive/car/")
    Call<ArrayList<Cars>> getArchive(@Query("sid") String sid);

    @FormUrlEncoded
    @POST("car/")
    Call<ArrayList<Cars>> getCars(@Field("model") String model, @Field("data0") String data0, @Field("data1") String data1);

    // модели
    @GET("model/")
    Call<ArrayList<Model>> getModel(@Query("brand") String brand);

    @GET("brand/")
    Call<ArrayList<Model>> getBrand();

    @GET("get_generation/")
    Call<ArrayList<Model>> getGeneration(@Query("model") String model);

    @GET("get_year_of_ssue/")
    Call<ArrayList<Model>> getYearOfSsue();

    @GET("get_city/")
    Call<ArrayList<Model>> getCity();

    @GET("get_color/")
    Call<ArrayList<Model>> getColor();

    @GET("get_ownerss/")
    Call<ArrayList<Model>> getOwnerss();

    @GET("get_engine_сapacity/")
    Call<ArrayList<Model>> getEngineCapacity();

    @GET("get_number_of_doors/")
    Call<ArrayList<Model>> getNumberOfDoors();

    @GET("get_body_type/")
    Call<ArrayList<Model>> getBodyType();
    @GET("get_condition/")
    Call<ArrayList<Model>> getCondition();
    @GET("get_drive/")
    Call<ArrayList<Model>> getDrive();
    @GET("get_rudder/")
    Call<ArrayList<Model>> getRudder();
    @GET("get_engine_type/")
    Call<ArrayList<Model>> getEngineType();
    @GET("get_equipment/")
    Call<ArrayList<Model>> getEquipment();

    @GET("arhive/")
    Call<Save> arhive(@Query("id") int id);

    @GET("unarhive/")
    Call<Save> deArhive(@Query("id") int id);

    // сохранение
    @Multipart
    @POST("images/add/")
    Call<Save> saveImages(@Part("sid") RequestBody sidRequest,
                          @Part("brand") RequestBody mBrandRequest,
                          @Part("model") RequestBody mModelRequest,
                          @Part("id") RequestBody id,
                          @Part("file\"; filename=\"image.png\" ") RequestBody fbody);

    @Multipart
    @POST("save_cars/")
    Call<Save> save(@Part("equipment") RequestBody mEquipmentRequest,
                    @Part("rudder") RequestBody mWheelRequest,
                    @Part("body") RequestBody mBodyRequest,
                    @Part("state") RequestBody mStateRequest,
                    @Part("engine") RequestBody mEngineRequest,
                    @Part("drive") RequestBody  mDriveRequest,
                    @Part("years") RequestBody  mYearOfIssueRequest,
                    @Part("vin") RequestBody  mVinRequest,
                    @Part("price") RequestBody  mPriceRequest,
                    @Part("mileage") RequestBody  mMileageRequest,
                    @Part("ownersByTCP") RequestBody  mOwnersByTCPRequest,
                    @Part("engineVolume") RequestBody  mEngineVolumeRequest,
                    @Part("color") RequestBody  mColorRequest,
                    @Part("numberOfDoors") RequestBody mNumberOfDoorsRequest,
                    @Part("city") RequestBody mCityRequest,
                    @Part("id") RequestBody idRequest,
                    @Part("generation") RequestBody mGeneration);

    // регистрация
    @FormUrlEncoded
    @POST("registration/")
    Call<Save> registration(@Field("email") String email,
                            @Field("passwd") String passwd,
                            @Field("first_name") String firstName,
                            @Field("last_name") String lastName,
                            @Field("phone") String phone);

    @GET("get/pin/")
    Call<Save> pin(@Query("email") String email);

    @GET("activate/")
    Call<Save> activate(@Query("email") String email, @Query("pin") String pk);

    @FormUrlEncoded
    @POST("auth/login/")
    Call<Auth> login(@Field("login") String login,
                     @Field("password") String password,
                     @Field("device_token") String deviceToken);

    @GET("auth/logout/")
    Call<Auth> logout(@Query("sid") String sid);
}
