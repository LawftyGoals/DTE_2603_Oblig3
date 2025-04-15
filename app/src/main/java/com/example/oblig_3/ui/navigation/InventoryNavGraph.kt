package com.example.oblig_3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oblig_3.ArtVendorScreen
import com.example.oblig_3.ui.start.StartDestination
import com.example.oblig_3.ui.start.StartOrderScreen

@Composable
fun InventoryNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, startDestination = StartDestination.route, modifier =
            modifier
    ) {
        composable(route = StartDestination.route) {
            StartOrderScreen(navigateToFilter = { filter ->
                navController.navigate(ArtVendorScreen.Filter.name)
                //viewModel.updateChosenFilter(filter)
            }, navigateToPurchase = { navController.navigate(ArtVendorScreen.Purchase.name) })
        }
    }

}