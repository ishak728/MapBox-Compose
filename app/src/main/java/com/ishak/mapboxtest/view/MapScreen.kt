package com.ishak.mapboxtest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.ishak.mapboxtest.R
import com.ishak.mapboxtest.viewmodel.MapScreenViewModel

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

@Composable
fun MapScreen(viewModel:MapScreenViewModel= hiltViewModel()) {

    println("mapscreen 1")

    viewModel. getSearchList("new",345)


    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        
       // Text(text = "xfgfdxfdxv")
        getList("new",345)
        //BottomSheetScreen()
        println("mapscreen 2")
    }
}

@Composable
fun getList(string: String,session_token:Int,viewModel: MapScreenViewModel= hiltViewModel()){

    val searchList by remember {
        viewModel.searchList
    }
    val isError by remember {
        viewModel.isError
    }
    val isLoading by remember {
        viewModel.isLoading
    }
println("search list::"+searchList)


   LaunchedEffect(searchList) {
       println("search list::"+searchList)
   }
    LazyColumn {
        items(searchList){
            Text(text = it.full_address)
        }
    }



}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen() {

    println("mapscreen 3")
    val scaffoldState= rememberBottomSheetScaffoldState()

    val scope= rememberCoroutineScope()

    BottomSheetScaffold(scaffoldState = scaffoldState ,
        sheetContent = {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(text = "helloooo")
                println("mapscreen 4")
               // BottomSheetContent(scaffoldState)
                //getList("new",345)
                println("mapscreen 5")
            }
        },
        sheetPeekHeight = 250.dp

    ) {

        println("mapscreen 10")

        /*MapBoxMappp(
            point = Point.fromLngLat(-0.6333, 35.6971),
            modifier = Modifier
                .fillMaxSize()
        )*/
        Text(text = "Isacccc")
        println("mapscreen 11")

    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(scaffoldState: BottomSheetScaffoldState) {
    var pickUpLocation by remember { mutableStateOf(TextFieldValue("")) }
    var whereTo by remember { mutableStateOf(TextFieldValue("")) }
    val scope= rememberCoroutineScope()
  Column(modifier= Modifier
      .height(800.dp)
      .padding(start = 20.dp)) {
      OutlinedTextField(
          value = pickUpLocation,
          leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "emailIcon") },
          trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "SearcIcon") },
          onValueChange = {
              pickUpLocation = it
              println("mapscreen 6")
          },
          label = { Text(text = "Pickup Location") },
          placeholder = { Text(text = "Search") },
          modifier = Modifier.clickable {
              println("mapscreen 7")
              //if SearchLocation is clicked then ı will show second bottomSheet
          },
          enabled = true
      )

      println("mapscreen 8")
      OutlinedTextField(
          value = whereTo,
          leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "emailIcon") },
          trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "SearcIcon") },
          onValueChange = {
              whereTo = it

          },
          label = { Text(text = "Where to?") },
          placeholder = { Text(text = "Search") },
          modifier = Modifier.clickable {

              //if SearchLocation is clicked then ı will show second bottomSheet
          },
          enabled = true
      )

  }
}





@Composable
fun MapBoxMappp(
    modifier: Modifier = Modifier,
    point: Point?,
) {
    println("mapscreen 9")
    val context = LocalContext.current
    val marker = remember(context) {
        context.getDrawable(R.drawable.location)!!.toBitmap()
    }
    var pointAnnotationManager: PointAnnotationManager? by remember {
        mutableStateOf(null)
    }
    AndroidView(
        factory = {
            MapView(it).also { mapView ->
                mapView.getMapboxMap().loadStyle(Style.STANDARD)
                val annotationApi = mapView.annotations
                pointAnnotationManager = annotationApi.createPointAnnotationManager()
            }
        },
        update = { mapView ->
            if (point != null) {
                pointAnnotationManager?.let {
                    it.deleteAll()
                    val pointAnnotationOptions = PointAnnotationOptions()
                        .withPoint(point)
                        .withIconImage(marker)

                    it.create(pointAnnotationOptions)
                    mapView.getMapboxMap()
                        .flyTo(CameraOptions.Builder().zoom(36.0).center(point).build())
                }
            }
            NoOpUpdate
        }
    )

}





