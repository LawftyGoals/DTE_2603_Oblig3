package com.example.oblig_3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.oblig_3.ArtVendorScreen
import com.example.oblig_3.ui.image.ImagePreviewScreen
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
    navController: NavHostController
) {


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
                navigateToImagePreview = { photoId ->

                    navController.navigate("${ImagePreviewDestination.routeWithArgs}/$photoId")
                },
                navigateBack = {
                    navController.popBackStack()
                })

        }
        composable(route = ImagePreviewDestination.routeWithArgs, arguments = listOf(navArgument(
            ImagePreviewDestination.IMAGE_ID_ARG) {type = NavType.IntType})) {
            ImagePreviewScreen(
                navigateToHomeScreen = {
                    navController.navigate(StartDestination.route)
                },
                navigateBack = {
                    navController.popBackStack()
                })
        }

    }

}