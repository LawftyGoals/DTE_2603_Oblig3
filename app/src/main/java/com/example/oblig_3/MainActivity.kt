package com.example.oblig_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oblig_3.ui.theme.Oblig_3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Oblig_3Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtVendorApp()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    Oblig_3Theme() {
        ArtVendorApp()
    }
}