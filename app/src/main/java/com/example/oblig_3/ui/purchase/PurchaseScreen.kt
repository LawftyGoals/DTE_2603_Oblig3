package com.example.oblig_3.ui.purchase

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oblig_3.ArtVendorAppTopBar
import com.example.oblig_3.R
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.ui.calculateTotalPrice
import com.example.oblig_3.ui.navigation.NavigationDestination
import com.example.oblig_3.ui.start.StartDestination
import com.example.oblig_3.ui.start.StartViewModel
import java.util.Locale

object PurchaseDestination : NavigationDestination {
    override val route = "purchase"
    override val titleRes = R.string.purchase

}

@Composable
fun PurchaseScreen(
    navigateBack: () -> Unit = {},
    viewModel: StartViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val shoppingCartState by viewModel.shoppingCartState.collectAsState()
    val purchaseItemList = shoppingCartState.purchaseItemList

    val ctx = LocalContext.current
    val toast = Toast.makeText(ctx, R.string.unimplemented, Toast.LENGTH_SHORT)

    Scaffold(topBar = {
        ArtVendorAppTopBar(
            currentScreen = StartDestination, canNavigateBack = true, navigateUp = navigateBack
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(R.string.total_price),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = String.format(
                        Locale.getDefault(), "%.2f", calculateTotalPrice(purchaseItemList)
                    ), style = MaterialTheme.typography.labelMedium
                )

                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        toast.show()
                    }) {
                    Text(text = stringResource(R.string.pay))
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewPurchaseScreen() {
    PurchaseScreen(listOf(PurchaseItem(photo = DataSource.photos[0])))
}*/