package com.hexan.ripper.network

import com.hexan.ripper.BuildConfig
import com.hexan.ripper.model.Authentication
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by James Cooper on 11/04/2018.
 */

interface RipperApiService {

    companion object {
        fun create(): RipperApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()

            return retrofit.create(RipperApiService::class.java)
        }
    }

    @FormUrlEncoded
    @POST("oauth/token")
    fun login(@Header("Authorization") credentials: String,
              @Field("grant_type") grantType: String,
              @Field("username") username: String,
              @Field("password") password: String):
            Observable<Authentication>

}