package com.example.kt_lr_3.ui.factory

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kt_lr_3.R
import com.example.kt_lr_3.ui.AppViewModelProvider
import com.example.kt_lr_3.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object FactoryDetailsDestination : NavigationDestination {
    override val route = "factory_details"
    override val titleRes = R.string.factory_detail_title
    const val factoryIdArg = "factoryDetailsId"
    val routeWithArgs = "$route/{$factoryIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactoryDetailsScreen(
    navigateToEditFactory: (String) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FactoryDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FactoryTopAppBar(
                title = stringResource(FactoryDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditFactory(uiState.factoryDetails.id.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_factory_title),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        FactoryDetailsBody(
            factoryDetailsUiState = uiState.factoryDetails,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteFactory()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun FactoryDetailsBody(
    factoryDetailsUiState: FactoryDetails,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        FactoryDetails(
            factoryDetails = factoryDetailsUiState, modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}


@Composable
fun FactoryDetails(
    factoryDetails: FactoryDetails, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            FactoryDetailsRow(label = R.string.label_name,factoryDetail = factoryDetails.name,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
            FactoryDetailsRow(label = R.string.label_number_of_workers,factoryDetail = factoryDetails.numberOfWorkers,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
            FactoryDetailsRow(label = R.string.label_number_of_workshops,factoryDetail = factoryDetails.numberOfWorkshops,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
            FactoryDetailsRow(label = R.string.label_worker_salary,factoryDetail = factoryDetails.workerSalary,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
            FactoryDetailsRow(label = R.string.label_master_salary,factoryDetail = factoryDetails.masterSalary,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
            FactoryDetailsRow(label = R.string.label_number_of_masters,factoryDetail = factoryDetails.numberOfMasters,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
            FactoryDetailsRow(label = R.string.label_profit_per_worker_per_month,factoryDetail = factoryDetails.profitPerWorkerPerMonth,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
            FactoryDetailsRow(label = R.string.label_profit_per_master_per_month,factoryDetail = factoryDetails.profitPerMasterPerMonth,modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))
        }
    }
}


@Composable
fun FactoryDetailsRow(@StringRes label: Int, factoryDetail: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = label),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp)) // Adjust spacing as needed
        Text(
            text = factoryDetail,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}


@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}