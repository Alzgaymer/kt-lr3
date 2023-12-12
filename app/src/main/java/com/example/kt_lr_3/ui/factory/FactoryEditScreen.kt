package com.example.kt_lr_3.ui.factory

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kt_lr_3.R
import com.example.kt_lr_3.ui.AppViewModelProvider
import com.example.kt_lr_3.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object FactoryEditDestination : NavigationDestination {
    override val route = "factory_edit"
    override val titleRes = R.string.edit_factory_title
    const val factoryIdArg = "factoryEditId"
    val routeWithArgs = "$route/{$factoryIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactoryEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FactoryEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
           FactoryTopAppBar(
                title = stringResource(FactoryEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        FactoryEntryBody(
            uiState = uiState,
            onFactoryValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateFactory()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())

        )
    }
}