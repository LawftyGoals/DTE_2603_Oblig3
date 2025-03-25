package com.example.oblig_3.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oblig_3.ui.data.Artist
import com.example.oblig_3.ui.data.Category
import com.example.oblig_3.ui.data.DataSource

@Composable
fun <T> FilterScreen(modifier: Modifier = Modifier, filterContent: List<T> = listOf<T>()) {
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        filterContent.mapIndexed { index, filter ->
                CategoryButton(filter= filter)
        }

    }
}

@Composable
fun <T> CategoryButton(modifier: Modifier = Modifier, filter: T){
    Button(modifier = modifier.fillMaxWidth(), onClick = {Log.i("FILTERSCREEN", "onclick")}){
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start){
            when (filter) {
                is Artist -> {
                    Text(fontSize = 16.sp,text=filter.name)
                    Text(text=filter.familyName)
                }
                is Category -> {
                    Text(text=filter.name)
                }
                else -> {
                    Text(text="Unrecognized Filter Type")
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun FilterPreview() {
    FilterScreen(filterContent = DataSource().photoCategories)
}