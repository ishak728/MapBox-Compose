package com.ishak.mapboxtest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap

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
fun MapScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        BottomSheetScreen()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen() {


    val scaffoldState= rememberBottomSheetScaffoldState()

    val scope= rememberCoroutineScope()

    BottomSheetScaffold(scaffoldState = scaffoldState ,
        sheetContent = {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                BottomSheetContent(scaffoldState)
            }
        },
        sheetPeekHeight = 250.dp

    ) {

        //add mapp

        MapBoxMappp(
            point = Point.fromLngLat(-0.6333, 35.6971),
            modifier = Modifier
                .fillMaxSize()
        )

    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(scaffoldState: BottomSheetScaffoldState) {
    var pickUpLocation by remember { mutableStateOf(TextFieldValue("")) }
    var whereTo by remember { mutableStateOf(TextFieldValue("")) }
    val scope= rememberCoroutineScope()
  Column(modifier= Modifier.height(500.dp)) {
      OutlinedTextField(
          value = pickUpLocation,
          leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "emailIcon") },
          trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "SearcIcon") },
          onValueChange = {
              pickUpLocation = it
              println("sasdfadfsdf")
          },
          label = { Text(text = "Pickup Location") },
          placeholder = { Text(text = "Search") },
          modifier = Modifier.clickable {
              println("searchBar clicked")
              //if SearchLocation is clicked then ı will show second bottomSheet
          },
          enabled = true
      )


      OutlinedTextField(
          value = whereTo,
          leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "emailIcon") },
          trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "SearcIcon") },
          onValueChange = {
              whereTo = it
              println("sasdfadfsdf")
          },
          label = { Text(text = "Where to?") },
          placeholder = { Text(text = "Search") },
          modifier = Modifier.clickable {
              println("searchBar clicked")
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
    val context = LocalContext.current
    val marker = remember(context) {
        context.getDrawable(R.drawable.ic_launcher_foreground)!!.toBitmap()
    }
    var pointAnnotationManager: PointAnnotationManager? by remember {
        mutableStateOf(null)
    }
    AndroidView(
        factory = {
            MapView(it).also { mapView ->
                mapView.getMapboxMap().loadStyle(Style.TRAFFIC_DAY)
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
                        .flyTo(CameraOptions.Builder().zoom(16.0).center(point).build())
                }
            }
            NoOpUpdate
        }
    )

}



