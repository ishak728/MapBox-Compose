package com.ishak.mapboxtest.retrofit

import com.ishak.mapboxtest.R
import com.ishak.mapboxtest.model.SearchList
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

//https://api.mapbox.com/search/searchbox/v1/suggest?q={search_text},session_token,access_token


    @GET("search/searchbox/v1/suggest")
    suspend fun getSearchList(
        @Query("q") string:String,
        @Query("session_token") session_token:Int,
        @Query("access_token") access_token: Int = R.string.mapbox_access_token
    ):SearchList


}