package com.example.oblig_3.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oblig_3.R
import com.example.oblig_3.ui.data.DataSource
import com.example.oblig_3.ui.data.PurchaseItem


@Composable
fun PurchaseScreen(purchaseItemList: List<PurchaseItem>) {
    val ctx = LocalContext.current
    val toast = Toast.makeText(ctx, R.string.unimplemented, Toast.LENGTH_SHORT)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Text(text = stringResource(R.string.total_price), fontSize = 16.sp, fontWeight = W700)
        Text(text = "${calculateTotalPrice(purchaseItemList)}", fontSize = 14.sp)

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