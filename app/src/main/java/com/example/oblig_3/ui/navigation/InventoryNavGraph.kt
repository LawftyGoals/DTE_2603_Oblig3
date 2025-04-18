package com.example.oblig_3.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oblig_3.ArtVendorScreen
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.ui.StartViewModel
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.image.FilterDestination
import com.example.oblig_3.ui.image.FilterScreen
import com.example.oblig_3.ui.start.StartDestination
import com.example.oblig_3.ui.start.StartOrderScreen

@Composable
fun InventoryNavHost( modifier:
                      Modifier = Modifier,navController: NavHostController, viewModel: StartViewModel = viewModel
    (factory = AppViewModelProvider.Factory)) {

    NavHost(
        navController = navController,
        startDestination = StartDestination.route,
        modifier = modifier
    ) {
        composable(route = StartDestination.route) {
            StartOrderScreen(viewModel = viewModel,
               navigateToFilter = { filter ->
                    navController.navigate(FilterDestination.route)
                },
                navigateToPurchase = { navController.navigate(ArtVendorScreen.Purchase.name) })
        }
        composable(route = FilterDestination.route) {
            FilterScreen(
                viewModel = viewModel,
                navigateToFilteredImages = {}, navigateBack = {navController.popBackStack()})
        }
    }

}