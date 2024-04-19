package com.ishak.mapboxtest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch



@Composable
fun PermissionScreeen(navController:NavHostController) {
    val context= LocalContext.current

    var shouldShowPermissionRationale by remember {
        mutableStateOf(
            ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)
        )
    }

    val accesFineLocation=android.Manifest.permission.ACCESS_FINE_LOCATION

    var isLocationGranted by remember { mutableStateOf(checkPermissionFor(accesFineLocation,context)) }

    val launcher= rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),onResult={ isGranted->
        if (isGranted){
            println("isGranted.....")
            isLocationGranted=true


            navController.navigate("map_screen")
        }

    })



    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { contentPadding ->
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
        Button(onClick = {
            if(ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)){
                shouldShowPermissionRationale=true
            }
            if (!isLocationGranted && !shouldShowPermissionRationale){
                launcher.launch(accesFineLocation)
            }

        }) {
            Text(text = "Location possition:$isLocationGranted")
        }

    }
}


fun checkPermissionFor(permission:String,context: Context):Boolean{
    return ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED
}

