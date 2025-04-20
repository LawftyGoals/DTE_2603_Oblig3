package com.example.oblig_3

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.oblig_3.ui.navigation.ArtVendorNavHost
import com.example.oblig_3.ui.navigation.NavigationDestination


enum class ArtVendorScreen(@StringRes val title: Int) {
    Start(title = R.string.visible_name),
    Filter(title = R.string.filter_screen),
    Images(title = R.string.images_screen),
    ImagePreview(title = R.string.image_preview_screen),
    Purchase(title = R.string.purchase)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtVendorAppTopBar(
    modifier: Modifier = Modifier,
    currentScreen: NavigationDestination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = { Text(stringResource(currentScreen.titleRes)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun ArtVendorApp(
    navController: NavHostController = rememberNavController()
) {

    ArtVendorNavHost(navController = navController)

    /*
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        ArtVendorScreen.valueOf(backStackEntry?.destination?.route ?: ArtVendorScreen.Start.name)

    Scaffold(topBar = {
        ArtVendorAppBar(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null && navController.currentDestination?.route != ArtVendorScreen.Start.name,
            navigateUp = {
                navController.navigateUp()
            })
    }) { innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ArtVendorScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            composable(route = ArtVendorScreen.Filter.name) {
                FilterScreen(
                    filterContent = if (uiState.chosenFilter == Filters.ARTIST) DataSource.artists else DataSource.categories,
                    onNextButtonClicked = { chosen ->
                        navController.navigate(ArtVendorScreen.Images.name)
                        viewModel.updateChosenArtistOrCategory(chosen)
                    }
                )
            }
            composable(route = ArtVendorScreen.Images.name) {
                var photos = listOf<Photo>()
                if (uiState.chosenArtist != null) {
                    photos =
                        DataSource.photos.filter { photo -> photo.artist.name == uiState.chosenArtist!!.name && photo.artist.familyName == uiState.chosenArtist!!.familyName }
                }
                if (uiState.chosenCategory != null) {
                    photos =
                        DataSource.photos.filter { photo -> photo.category == uiState.chosenCategory }
                }
                ImageScreen(photos = photos, onNextButtonClick = { photo ->
                    navController.navigate(ArtVendorScreen.ImagePreview.name)
                    viewModel.updateCurrentPurchaseItem(PurchaseItem(photo = photo))
                    viewModel.setTargetPhoto(photo)
                })
            }
            composable(route = ArtVendorScreen.ImagePreview.name) {
                ImagePreviewScreen(
                    photo = uiState.targetPhoto,
                    currentPurchaseItem = uiState.currentPurchaseItem!!,
                    updateCurrentPurchaseItem = { purchaseItem -> viewModel.updateCurrentPurchaseItem(purchaseItem) },
                    onNextButtonClicked = { purchaseItem: PurchaseItem? ->
                        if (purchaseItem != null) {
                            viewModel.updatePurchaseItemCart(purchaseItem)
                        }
                        navController.navigate(ArtVendorScreen.Start.name)
                    })
            }
            composable(route = ArtVendorScreen.Purchase.name) {
                PurchaseScreen(purchaseItemList = uiState.purchaseItemCart)
            }


        }
    }*/
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewAppBar(){
    ArtVendorAppTopBar(Modifier, ArtVendorScreen.Start, false) { }
}*/