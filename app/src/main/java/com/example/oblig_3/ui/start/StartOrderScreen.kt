package com.example.oblig_3.ui.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.oblig_3.R
import com.example.oblig_3.ui.AppViewModelProvider
import com.example.oblig_3.ui.calculateTotalPrice
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.navigation.NavigationDestination
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oblig_3.ArtVendorAppTopBar
import com.example.oblig_3.ui.ArtVendorViewModel
import com.example.oblig_3.ui.data.DataSource.photos
import com.example.oblig_3.ui.data.PurchaseItemDto
import com.example.oblig_3.ui.data.testPhoto
import java.util.Locale


object StartDestination : NavigationDestination {
    override val route = "start"
    override val titleRes = R.string.visible_name
}

@Composable
fun StartOrderScreen(
    modifier: Modifier = Modifier,
    viewModel: ArtVendorViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToFilter: (Filters) -> Unit = {},
    navigateToPurchase: () -> Unit = {}
) {

    val shoppingCartState by viewModel.shoppingCartState.collectAsState()
    Scaffold(modifier = modifier, topBar = {
        ArtVendorAppTopBar(
            currentScreen = StartDestination,
            canNavigateBack = false
        )
    }) { innerPadding ->
        StartOrderBody(
            modifier = modifier.padding(innerPadding),
            navigateToFilter = navigateToFilter,
            updateChosenFilter = { filter ->
                viewModel.updateChosenFilter(filter)
            },
            purchaseItemCart = shoppingCartState.purchaseItemList.map{purchaseItem ->
                val photo = photos.find{ photo -> photo.id ==
                        purchaseItem.photoId.toLong()} ?: testPhoto
                val photoSize = purchaseItem.photoSize
                PurchaseItemDto(id = purchaseItem.id, photo=photo, photoSize=photoSize, frameType
                = purchaseItem.frameType, frameSize = purchaseItem.frameSize
                )},
            deleteFromPurchaseItemCart = { id -> viewModel.removeFromShoppingCart(id) },
            navigateToPurchase
        )
    }

}

@Composable
fun StartOrderBody(
    modifier: Modifier = Modifier, navigateToFilter: (Filters) -> Unit,
    updateChosenFilter: (Filters) -> Unit, purchaseItemCart: List<PurchaseItemDto>,
    deleteFromPurchaseItemCart: (Int) -> Unit, navigateToPurchase: () -> Unit
) {

    val totalCost = calculateTotalPrice(purchaseItemCart)
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))
    ) {
        Text(
            stringResource(R.string.choose_image_based_on),
            style = MaterialTheme.typography.labelLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))
        ) {
            Button(modifier = Modifier.weight(1f), onClick = {
                updateChosenFilter(Filters.ARTIST)
                navigateToFilter(Filters.ARTIST)
            }) {
                Text(stringResource(R.string.artist))
            }
            Button(modifier = Modifier.weight(1f), onClick = {
                updateChosenFilter(Filters.CATEGORY)
                navigateToFilter(Filters.CATEGORY)
            }) {
                Text(stringResource(R.string.category))
            }
        }

        Text(
            "${stringResource(R.string.number_of_images_chosen)} ${purchaseItemCart.count()}",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            "${stringResource(R.string.total_price)} ${
                String.format(
                    Locale.getDefault(),
                    "%.2f",
                    totalCost
                )
            }", style = MaterialTheme.typography.labelLarge
        )

        if (purchaseItemCart.count() > 0) {

            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
            ) {
                purchaseItemCart.map { purchaseItem ->
                    PurchaseItemCard(
                        purchaseItem = purchaseItem,
                        onDeleteClicked = { deleteFromPurchaseItemCart(purchaseItem.id ?: 0) })
                }
            }
        }

        Button(modifier = Modifier.fillMaxWidth(), onClick = navigateToPurchase) {
            Text(stringResource(R.string.purchase))
        }
    }

}


@Composable
fun PurchaseItemCard(
    modifier: Modifier = Modifier,
    purchaseItem: PurchaseItemDto,
    onDeleteClicked: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_small))
            )
            .padding(dimensionResource(R.dimen.padding_small)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                style = MaterialTheme.typography.displayMedium,
                text = purchaseItem.photo.artist.name.toString()
            )
            Text(text = purchaseItem.photo.artist.familyName.toString())
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(purchaseItem.frameType.title))
            Text(text = stringResource(purchaseItem.photoSize.title))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = purchaseItem.frameSize.size.toString())
            Text(text = String.format(Locale.getDefault(), "%.2f", purchaseItem.photo.price))
        }
        IconButton(
            modifier = Modifier
                .testTag(stringResource(R.string.delete))
                .weight(0.6f)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_medium))
                ),
            onClick = { onDeleteClicked(purchaseItem.id ?: 0) }) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete purchase item")
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun Preview() {
    Oblig_3Theme{
        StartOrderScreen(
            onDeleteClicked = {},
            purchaseItemCart = listOf(
                PurchaseItem(photo = DataSource.photos[0]),
                PurchaseItem(photo = DataSource.photos[9])
            )
        )

    }
}*/