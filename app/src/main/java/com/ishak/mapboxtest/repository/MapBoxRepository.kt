package com.ishak.mapboxtest.repository

import com.ishak.mapboxtest.model.SearchList
import com.ishak.mapboxtest.retrofit.RetrofitApi
import com.ishak.mapboxtest.util.Resource
import javax.inject.Inject

class MapBoxRepository @Inject constructor(
    val retrofitApi: RetrofitApi
):MapBoxRepositoryInterface {
    override suspend fun getSearchList(string: String,session_token:Int): Resource<SearchList> {
        return try {
            println("mapboxrepository nyes data ")
            Resource.Success(retrofitApi.getSearchList(string,session_token))
        }catch (e:Exception){
            println("mapboxrepository no data :::"+e)
            Resource.Error("no data")

        }
    }
}