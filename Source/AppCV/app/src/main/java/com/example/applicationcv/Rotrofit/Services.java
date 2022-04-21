package com.example.applicationcv.Rotrofit;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Services {

    @POST("qncv/addmoreinfo")
    @FormUrlEncoded
    Observable<String> addInfoUser(
            @Field("email") String email,
            @Field("fullName") String fullName,
            @Field("sex") String sex,
            @Field("birth") String birth,
            @Field("idCard") String idCard,
            @Field("idBHYT") String idBHYT,
            @Field("national") String national,
            @Field("address") String address,
            @Field("numPhone") String numPhone,
            @Field("vaccine") String vaccine);

    @POST("qncv/phananh")
    @FormUrlEncoded
    Observable<String> sendPhananh(
            @Field("phananh") String phananh);

    @POST("qncv/kbyt")
    @FormUrlEncoded
    Observable<String> sendKbyt(
            @Field("kbyt") String kbyt);

    @POST("qncv/register")
    @FormUrlEncoded
    Observable<String> registerUser(
            @Field("email") String email,
            @Field("name") String name,
            @Field("password") String password);

    @POST("qncv/login")
    @FormUrlEncoded
    Observable<String> loginUser(
            @Field("email") String email,
            @Field("password") String password);
}
