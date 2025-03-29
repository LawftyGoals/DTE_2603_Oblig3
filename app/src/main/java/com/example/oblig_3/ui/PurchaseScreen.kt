package com.example.oblig_3.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.oblig_3.R
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.PurchaseItem


@Composable
fun PurchaseScreen(purchaseItemList: List<PurchaseItem>) {
    val ctx = LocalContext.current
    val toast = Toast.makeText(ctx, R.string.unimplemented, Toast.LENGTH_SHORT)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(R.dimen.padding_small))) {
        Text(text = stringResource(R.string.total_price), style= MaterialTheme.typography.labelLarge)
        Text(text = "${calculateTotalPrice(purchaseItemList)}", style=MaterialTheme.typography.labelMedium)

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                toast.show()
            }) {
            Text(text = stringResource(R.string.pay))
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewPurchaseScreen() {
    PurchaseScreen(listOf(PurchaseItem(DataSource.photos[0])))
}