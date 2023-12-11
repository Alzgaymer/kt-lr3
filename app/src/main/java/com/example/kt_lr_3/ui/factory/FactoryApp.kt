package com.example.kt_lr_3.ui.factory

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kt_lr_3.R
import com.example.kt_lr_3.ui.navigation.FactoryNavHost

@Composable
fun FactoryApp(navController: NavHostController = rememberNavController()) {
    FactoryNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactoryTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier =Modifier,
) {
    CenterAlignedTopAppBar(
        title = { Text(title)},
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}