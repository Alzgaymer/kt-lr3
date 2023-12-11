package com.example.kt_lr_3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.Dispatchers

@Composable
fun FactoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    val homeViewModel: HomeViewModel =
        viewModel<HomeViewModel>(factory = AppViewModelProvider.Factory)
    val factoryEntryViewModel: FactoryEntryViewModel =
        viewModel<FactoryEntryViewModel>(factory = AppViewModelProvider.Factory)
    val factoryDetailsViewModel: FactoryDetailsViewModel =
        viewModel<FactoryDetailsViewModel>(factory = AppViewModelProvider.Factory)
    val factoryEditViewModel: FactoryEditViewModel =
        viewModel<FactoryEditViewModel>(factory = AppViewModelProvider.Factory)
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier,
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                factories = homeViewModel.uiState.value.factories,
                navigateToFactoryEntry = {navController.navigate(FactoryEntryDestination.route)},
                navigateToFactoryUpdate = {
                    navController.navigate("${FactoryDetailsDestination.route}/${it}")
                }
            )
        }

        composable(route = FactoryEntryDestination.route) {
            val uiState = factoryEntryViewModel.uiState.collectAsState().value
            FactoryEntryScreen(
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
                uiState = uiState,
                updateUiState = factoryEntryViewModel::updateUiState,
                saveFactory = factoryEntryViewModel::saveFactory,
            )
        }

        composable(
            route = FactoryDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(FactoryDetailsDestination.factoryIdArg){
                type = NavType.StringType})
        ){
            FactoryDetailsScreen(
                    navigateToEditFactory = {navController.navigate("${FactoryDetailsDestination.route}/$it")} ,
                    navigateBack = {navController.navigateUp()},
                    uiState = factoryDetailsViewModel.uiState.value,
                    deleteFactory = factoryDetailsViewModel::deleteFactory,
                )
        }

        composable(
            route = FactoryEditDestination.routeWithArgs,
            arguments = listOf(navArgument(FactoryEditDestination.itemIdArg) {
                type = NavType.StringType})
        ){
            val uiState = factoryEditViewModel.uiState.collectAsState().value
            FactoryEditScreen(
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
                uiState = uiState,
                updateUiState = factoryEditViewModel::updateUiState,
                updateFactory = factoryEditViewModel::updateFactory,
            )
        }

    }
}