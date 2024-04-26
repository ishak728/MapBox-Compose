package com.ishak.mapboxtest.repository

import com.ishak.mapboxtest.model.SearchList
import com.ishak.mapboxtest.util.Resource

interface MapBoxRepositoryInterface {


    suspend fun getSearchList(string: String,session_token:Int):Resource<SearchList>
}