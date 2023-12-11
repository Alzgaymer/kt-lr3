package com.example.kt_lr_3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kt_lr_3.ui.factory.*
import com.example.kt_lr_3.ui.home.HomeDestination
import com.example.kt_lr_3.ui.home.HomeScreen

@Composable
fun FactoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier,
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToFactoryEntry = {navController.navigate(FactoryEntryDestination.route)},
                navigateToFactoryUpdate = {
                    navController.navigate("${FactoryDetailsDestination.route}/${it}")
                }
            )
        }

        composable(route = FactoryEntryDestination.route) {
            FactoryEntryScreen(
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
            )
        }

        composable(
            route = FactoryDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(FactoryDetailsDestination.factoryIdArg){
                type = NavType.StringType})
        ){
                FactoryDetailsScreen(
                    navigateToEditFactory = {id -> navController.navigate("${FactoryEditDestination.route}/$id")} ,
                    navigateBack = {navController.navigateUp()},
                )
        }

        composable(
            route = FactoryEditDestination.routeWithArgs,
            arguments = listOf(navArgument(FactoryEditDestination.factoryIdArg) {
                type = NavType.StringType})
        ){
            FactoryEditScreen(
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
                )
        }

    }
}