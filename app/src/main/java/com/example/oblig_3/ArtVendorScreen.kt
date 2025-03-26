package com.example.oblig_3

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.oblig_3.ui.ArtVendorViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oblig_3.ui.FilterScreen
import com.example.oblig_3.ui.ImagePreviewScreen
import com.example.oblig_3.ui.ImageScreen
import com.example.oblig_3.ui.StartOrderScreen
import com.example.oblig_3.ui.data.DataSource


enum class ArtVendorScreen(@StringRes val title: Int) {
    Start(title = R.string.visible_name),
    Filter(title = R.string.filter_screen),
    Images(title = R.string.images_screen),
    ImagePreview(title = R.string.image_preview_screen)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtVendorAppBar(modifier: Modifier = Modifier, currentScreen: ArtVendorScreen, canNavigateBack: Boolean, navigateUp: () -> Unit ){
    TopAppBar(
        modifier = modifier,
        title={Text(stringResource(currentScreen.title))},
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = navigateUp){
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                }
            }
        }
    )
}

@Composable
fun ArtVendorApp(viewModel: ArtVendorViewModel = viewModel(), navController: NavHostController = rememberNavController()){

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ArtVendorScreen.valueOf( backStackEntry?.destination?.route ?: ArtVendorScreen.Start.name )

    Scaffold(topBar = {
        ArtVendorAppBar(currentScreen = currentScreen, canNavigateBack = navController.previousBackStackEntry != null, navigateUp = {navController.navigateUp()})
    }) {
        innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ArtVendorScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ){
            composable(route=ArtVendorScreen.Start.name) {
                StartOrderScreen(onNextButtonClicked = {

                    navController.navigate(ArtVendorScreen.Filter.name)
                })
            }
            composable (route = ArtVendorScreen.Filter.name) {
                FilterScreen(filterContent = DataSource.artists)
            }
            composable(route = ArtVendorScreen.Images.name) {
                ImageScreen()
            }
            composable(route = ArtVendorScreen.ImagePreview.name) {
                ImagePreviewScreen(photo = DataSource.photos[0])
            }


        }
    }
}