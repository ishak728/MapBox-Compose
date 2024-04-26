package com.ishak.mapboxtest.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun Navigation(navController:NavHostController) {


    

    
    
    NavHost(navController = navController, startDestination = "permission_screen"){

        composable(route="permission_screen"){

            PermissionScreeen(navController)

        }

        composable("map_screen"){

            MapScreen()
        }

    }
    
    

}