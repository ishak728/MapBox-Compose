package com.ishak.mapboxtest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionScreeen(navController:NavHostController) {
    val context= LocalContext.current

    var shouldShowPermissionRationale by remember {
        mutableStateOf(
            ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)
        )
    }

    val accesFineLocation=Manifest.permission.ACCESS_FINE_LOCATION

    var isLocationGranted by remember { mutableStateOf(checkPermissionFor(accesFineLocation,context)) }

    val launcher= rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
        onResult={ isGranted->


        if (isGranted){
            println("isGranted.....")
            isLocationGranted=true


           val locationManager=context.getSystemService(ComponentActivity.LOCATION_SERVICE) as LocationManager
           if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ){
               val lastLocation= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

               if(lastLocation!=null){
                   println("latititititude :: "+lastLocation.latitude)
               }
               else{
                   println("nulllllll")
               }
           }



            navController.navigate("map_screen")
        }

    })



    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    })
    { contentPadding ->
        contentPadding

        if (shouldShowPermissionRationale) {
            LaunchedEffect(Unit) {
                scope.launch {
                    val userAction = snackbarHostState.showSnackbar(
                        message ="Permission Needed",
                        actionLabel = "Approve",
                        duration = SnackbarDuration.Indefinite,
                        withDismissAction = true
                    )
                    when (userAction) {
                        SnackbarResult.ActionPerformed -> {
                            shouldShowPermissionRationale = false
                            //isLocationGranted=true
                            launcher.launch(accesFineLocation)
                        }
                        SnackbarResult.Dismissed -> {
                            shouldShowPermissionRationale = false
                        }
                    }
                }
            }
        }
    }
    Column {

        OutlinedTextField(
            value = "Where to?",
            ///colors = TextFieldDefaults.outlinedTextFieldColors(b = Color.Red),
            textStyle = TextStyle(fontSize =30.sp, fontWeight = FontWeight(50) ),
            leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "emailIcon") },
            trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            onValueChange = {

                println("sasdfadfsdf")
            },
            label = { Text(text = "Location") },
            placeholder = { Text(text = "Search") },
            modifier = Modifier.clickable {
                println("searchBar clicked")

                if(ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)){
                    shouldShowPermissionRationale=true
                }
                else if (!isLocationGranted && !shouldShowPermissionRationale){
                    launcher.launch(accesFineLocation)
                }
               else if(isLocationGranted){
                    navController.navigate("map_screen")
                }
            },
            enabled = false
        )

    }
}

fun checkPermissionFor(permission:String,context: Context):Boolean{
    return ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED
}

