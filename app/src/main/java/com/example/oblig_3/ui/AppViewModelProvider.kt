package com.example.oblig_3.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.oblig_3.ArtVendorApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { StartViewModel(artVendorApplication().appContainer.purchaseItemsRepository) }
    }
}

fun CreationExtras.artVendorApplication(): ArtVendorApplication = (this[ViewModelProvider
    .AndroidViewModelFactory.APPLICATION_KEY] as ArtVendorApplication)