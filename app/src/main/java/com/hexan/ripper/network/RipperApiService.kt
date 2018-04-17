package com.hexan.ripper.network

import com.hexan.ripper.BuildConfig
import com.hexan.ripper.manager.SharedPreferenceManager
import com.hexan.ripper.model.Authentication
import com.hexan.ripper.model.Playlist
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.hexan.ripper.model.Song


/**
 * Created by James Cooper on 11/04/2018.
 */

interface RipperApiService {

    companion object {
        fun create(): RipperApiService {

            val bearerToken = SharedPreferenceManager.getAccessToken()
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                if (!bearerToken.isNullOrEmpty()) {
                    builder.addHeader("Authorization", "Bearer " + bearerToken)
                }
                chain.proceed(builder.build())
            }.addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                if (response.code() == 401) {
                    RxNetworkErrorBus.send(401)

                }
                response
            }.build()

            val retrofit = Retrofit.Builder()
                    .client(client)
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

    @GET("playlist/findall")
    fun getPlaylists(): Observable<List<Playlist>>

    @POST("playlist")
    fun createPlaylist(@Body playlist: Playlist): Observable<Playlist>

    @GET("playlist/{playlistId}/songs")
    fun getPlaylistSongs(@Path("playlistId") playlistId: Long?): Observable<List<Song>>

    fun getSongs(): Any
}