package com.example.kt_lr_3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kt_lr_3.ui.AppViewModelProvider
import com.example.kt_lr_3.ui.factory.*
import com.example.kt_lr_3.ui.home.HomeDestination
import com.example.kt_lr_3.ui.home.HomeScreen
import com.example.kt_lr_3.ui.home.HomeViewModel

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
            val viewModel: HomeViewModel =
                 viewModel<HomeViewModel>(factory = AppViewModelProvider.Factory)
            HomeScreen(
                factories = viewModel.uiState.value.factories,
                navigateToFactoryEntry = {navController.navigate(FactoryEntryDestination.route)},
                navigateToFactoryUpdate = {
                    navController.navigate("${FactoryDetailsDestination.route}/${it}")
                }
            )
        }

        composable(route = FactoryEntryDestination.route) {
            val viewModel: FactoryEntryViewModel =
                viewModel<FactoryEntryViewModel>(factory = AppViewModelProvider.Factory)
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            FactoryEntryScreen(
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
                uiState = uiState,
                updateUiState = viewModel::updateUiState,
                saveFactory = viewModel::saveFactory,
            )
        }

        composable(
            route = FactoryDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(FactoryDetailsDestination.factoryIdArg){
                type = NavType.StringType})
        ){
                val viewModel: FactoryDetailsViewModel =
                   viewModel<FactoryDetailsViewModel>(factory = AppViewModelProvider.Factory)
                FactoryDetailsScreen(
                    navigateToEditFactory = {navController.navigate("${FactoryDetailsDestination.route}/$it")} ,
                    navigateBack = {navController.navigateUp()},
                    uiState = viewModel.uiState.value,
                    deleteFactory = viewModel::deleteFactory,
                )
        }

        composable(
            route = FactoryEditDestination.routeWithArgs,
            arguments = listOf(navArgument(FactoryEditDestination.itemIdArg) {
                type = NavType.StringType})
        ){
            val viewModel: FactoryEditViewModel =
                 viewModel<FactoryEditViewModel>(factory = AppViewModelProvider.Factory)
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

            FactoryEditScreen(
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
                uiState = uiState,
                updateUiState = viewModel::updateUiState,
                updateFactory = viewModel::updateFactory,
            )
        }

    }
}