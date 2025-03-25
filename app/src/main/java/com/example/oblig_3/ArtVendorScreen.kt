package com.example.oblig_3

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.oblig_3.ui.ArtVendorViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oblig_3.ui.StartOrderScreen

enum class ArtVendorScreen(@StringRes val title: Int) {
    Start(title = R.string.visible_name)

}

@Composable
fun ArtVendorApp(viewModel: ArtVendorViewModel = viewModel(), navController: NavHostController = rememberNavController()){

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ArtVendorScreen.valueOf( backStackEntry?.destination?.route ?: ArtVendorScreen.Start.name )

    Scaffold(topBar = {
        ArtVendorAppBar(currentScreen)
    }) {
        innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ArtVendorScreen.Start.name,
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(innerPadding)
        ){
            composable(route=ArtVendorScreen.Start.name) {
                val context = LocalContext.current
                StartOrderScreen()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtVendorAppBar(currentScreen: ArtVendorScreen){
    TopAppBar(
        title={Text(stringResource(currentScreen.title))}
    )
}