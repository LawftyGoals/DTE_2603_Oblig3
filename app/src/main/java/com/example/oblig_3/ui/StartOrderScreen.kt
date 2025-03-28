package com.example.oblig_3.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oblig_3.R
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.data.PurchaseItem


@Composable
fun StartOrderScreen(modifier: Modifier = Modifier, purchaseItemList: List<PurchaseItem>, onNextButtonClicked: (Filters) -> Unit = {}){

    var totalCost by remember { mutableFloatStateOf(calculateTotalPrice(purchaseItemList)) }

    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(stringResource(R.string.placeholder))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = { onNextButtonClicked(Filters.ARTIST) }) {
                Text(stringResource(R.string.placeholder))
            }
            Button(onClick = { onNextButtonClicked(Filters.CATEGORY) }) {
                Text(stringResource(R.string.placeholder))
            }
        }

        Text(stringResource(R.string.placeholder) + "${purchaseItemList.count()}")

        Text(stringResource(R.string.placeholder) + "$totalCost")

        if(purchaseItemList.count()> 0){

            Column(modifier= Modifier.padding(8.dp)){
                purchaseItemList.map {purchaseItem ->
                    PurchaseItemCard(purchaseItem = purchaseItem)}
            }
        }

        Button(modifier = Modifier.fillMaxWidth(), onClick = {Log.i("STARTSCREEN", "onclick")}) {
            Text(stringResource(R.string.placeholder))
        }
    }
}


@Composable
fun PurchaseItemCard(modifier: Modifier = Modifier, purchaseItem: PurchaseItem){
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)){
            Text(text=purchaseItem.photo?.artist?.name.toString())
            Text(text=purchaseItem.photo?.artist?.familyName.toString())
        }
        Column(modifier = Modifier.weight(1f)){
            Text(text=stringResource(purchaseItem.frameType.title))
            Text(text=stringResource(purchaseItem.size.title))
        }
        Column(modifier = Modifier.weight(1f)){
            Text(text=purchaseItem.frameSize.size.toString())
            Text(text=purchaseItem.getCost().toString())
        }
        IconButton(modifier = Modifier.weight(1f), onClick={Log.i("STARTSCREEN DELETE PURCAHSE ITEM", "PLACEHOLLDER")}){
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete purchase item")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    StartOrderScreen(purchaseItemList = listOf(PurchaseItem(DataSource.photos[0]), PurchaseItem(DataSource.photos[9])))
}