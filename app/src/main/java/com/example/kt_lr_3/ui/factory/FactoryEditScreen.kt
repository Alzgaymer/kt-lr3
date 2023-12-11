package com.example.kt_lr_3.ui.factory

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.kt_lr_3.R
import com.example.kt_lr_3.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object FactoryEditDestination : NavigationDestination {
    override val route = "factory_edit"
    override val titleRes = R.string.edit_factory_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactoryEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    uiState: FactoryUiState,
    updateUiState: (FactoryDetails) -> Unit,
    updateFactory: suspend ()-> Unit,
    modifier: Modifier = Modifier,
) {
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
            onFactoryValueChange = updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    updateFactory()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}