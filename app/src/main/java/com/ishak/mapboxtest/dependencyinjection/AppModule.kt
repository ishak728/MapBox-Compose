package com.ishak.mapboxtest.dependencyinjection

import com.ishak.mapboxtest.repository.MapBoxRepository
import com.ishak.mapboxtest.repository.MapBoxRepositoryInterface
import com.ishak.mapboxtest.retrofit.RetrofitApi
import com.ishak.mapboxtest.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun retrofit():RetrofitApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Provides
    @Singleton
    fun MapBoxRepositoryInterface(retrofitApi: RetrofitApi):MapBoxRepositoryInterface{
        return MapBoxRepository(retrofitApi)
    }

}