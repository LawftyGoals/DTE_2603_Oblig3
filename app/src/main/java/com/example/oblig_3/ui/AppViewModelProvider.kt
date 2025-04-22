package com.example.oblig_3.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.oblig_3.ArtVendorApplication
import com.example.oblig_3.ui.image.FilterViewModel
import com.example.oblig_3.ui.image.ImagesViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            ArtVendorViewModel(
                artVendorApplication().appContainer
                    .purchaseItemsRepository
            )
        }

        initializer {
            FilterViewModel(
                this.createSavedStateHandle(),
                artVendorApplication().appContainer.artRepository
            )
        }

        initializer {
            ImagesViewModel(
                this.createSavedStateHandle(),
                artVendorApplication().appContainer.artRepository
            )
        }

        initializer { StartViewModel(artVendorApplication().appContainer.purchaseItemsRepository) }

    }

}

fun CreationExtras.artVendorApplication(): ArtVendorApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ArtVendorApplication)