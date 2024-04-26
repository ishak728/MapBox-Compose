package com.ishak.mapboxtest.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishak.mapboxtest.model.SearchList
import com.ishak.mapboxtest.model.Suggestion
import com.ishak.mapboxtest.repository.MapBoxRepositoryInterface
import com.ishak.mapboxtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    val mapBoxRepositoryInterface: MapBoxRepositoryInterface
): ViewModel() {

    /*init {
        println("initttt")
        getSearchList("new",345)
    }*/

    val searchList= mutableStateOf<List<Suggestion>>(listOf())
    var isError= mutableStateOf(false)
    var isLoading= mutableStateOf(false)


    fun getSearchList(string: String,session_token:Int){
        isLoading.value=true
        viewModelScope.launch {
            val result=mapBoxRepositoryInterface.getSearchList(string,session_token)

           println(result.data)
            when(result){
                is Resource.Success->{
                    println("succes")
                    searchList.value= result.data?.suggestions ?: listOf()
                    isLoading.value=false
                    isError.value=false

                }
                is Resource.Error->{
                    println("erroror")
                    isError.value=true
                    isLoading.value=false
                }
                is Resource.Loading->{
                    println("loadinggggg")
                    isLoading.value=true
                    isError.value=false
                }
            }
        }
    }

}