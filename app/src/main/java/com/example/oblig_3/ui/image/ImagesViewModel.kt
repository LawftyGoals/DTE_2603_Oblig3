package com.example.oblig_3.ui.image

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.oblig_3.ui.data.DataSource.photos
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.data.Photo

class ImagesViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle[ImagesDestination.ID_ARG])
    private val filterType: String = checkNotNull(
        savedStateHandle[FilterDestination
            .FILTER_TYPE_ARG]
    )

    var imagesUiState by mutableStateOf(ImagesUiState())

    init {
        Log.i("imagesfilter", "$id $filterType")
        imagesUiState = ImagesUiState(photos.filter { photo ->
            if(filterType == Filters.ARTIST.name)  photo.artist.id == id
            else photo.category.id == id
        })
    }

}

data class ImagesUiState(val filteredPhotos: List<Photo> = listOf())