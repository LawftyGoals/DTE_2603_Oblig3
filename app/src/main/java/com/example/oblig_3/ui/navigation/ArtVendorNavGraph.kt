package com.example.oblig_3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.oblig_3.ArtVendorScreen
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.ui.ArtVendorViewModel
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.image.ImagePreviewScreen
import com.example.oblig_3.ui.data.PurchaseItemDto
import com.example.oblig_3.ui.image.FilterDestination
import com.example.oblig_3.ui.image.FilterScreen
import com.example.oblig_3.ui.image.ImagePreviewDestination
import com.example.oblig_3.ui.image.ImagesDestination
import com.example.oblig_3.ui.image.ImagesScreen
import com.example.oblig_3.ui.start.StartDestination
import com.example.oblig_3.ui.start.StartOrderScreen

@Composable
fun ArtVendorNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ArtVendorViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = StartDestination.route,
        modifier = modifier
    ) {
        composable(route = StartDestination.route) {
            StartOrderScreen(navigateToFilter = { filter ->
                navController.navigate("${FilterDestination.route}/${filter.name}")
            }, navigateToPurchase = { navController.navigate(ArtVendorScreen.Purchase.name) })
        }
        composable(route = FilterDestination.routeWithArgs, arguments = listOf(navArgument(
            FilterDestination.FILTER_TYPE_ARG){ type = NavType.StringType })) {
            FilterScreen(
                navigateToFilteredImages = { filter, id ->
                    navController.navigate("${ImagesDestination.route}/${filter}/${id}")
                },
                navigateBack = {
                    navController.popBackStack()
                })
        }
        composable(route = ImagesDestination.routeWithArgs, arguments = listOf(navArgument(
            ImagesDestination.FILTER_TYPE_ARG){type = NavType.StringType}, navArgument(
            ImagesDestination.ID_ARG){type = NavType.IntType}
        )) {
            ImagesScreen(
                navigateToImagePreview = { photo ->
                    viewModel.updateCurrentPurchaseItem(PurchaseItemDto(id = 1, photo = photo))
                    viewModel.setTargetPhoto(photo)
                    navController.navigate(ImagePreviewDestination.route)
                },
                navigateBack = {
                    navController.popBackStack()
                })

        }
        composable(route = ImagePreviewDestination.route) {
            ImagePreviewScreen(
                photo = uiState.targetPhoto,
                currentPurchaseItem = uiState.currentPurchaseItem!!,
                updateCurrentPurchaseItem = { purchaseItem ->
                    viewModel.updateCurrentPurchaseItem(
                        purchaseItem
                    )
                },
                navigateToHomeScreen = { purchaseItem: PurchaseItemDto? ->
                    if (purchaseItem != null) {
                        //viewModel.addToShoppingCart(purchaseItem)
                    }
                    navController.navigate(ArtVendorScreen.Start.name)
                },
                navigateBack = {
                    navController.popBackStack()
                })
        }

    }

}