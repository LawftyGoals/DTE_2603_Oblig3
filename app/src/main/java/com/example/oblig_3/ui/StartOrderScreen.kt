package com.example.oblig_3.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oblig_3.R


@Composable
fun StartOrderScreen(modifier: Modifier = Modifier){

    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(stringResource(R.string.placeholder))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {Log.i("STARTSCREEN", "onclick")}) {
                Text(stringResource(R.string.placeholder))
            }
            Button(onClick = {Log.i("STARTSCREEN", "onclick")}) {
                Text(stringResource(R.string.placeholder))
            }
        }

        Text(stringResource(R.string.placeholder))

        Text(stringResource(R.string.placeholder))

        Button(modifier = Modifier.fillMaxWidth(), onClick = {Log.i("STARTSCREEN", "onclick")}) {
            Text(stringResource(R.string.placeholder))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    StartOrderScreen()
}