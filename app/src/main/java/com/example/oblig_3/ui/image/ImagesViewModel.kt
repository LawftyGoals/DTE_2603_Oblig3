package com.example.oblig_3.ui.image

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oblig_3.network.ArtApi
import com.example.oblig_3.network.NetworkState
import com.example.oblig_3.network.PhotoDto
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.data.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImagesViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle[ImagesDestination.ID_ARG])
    private val filterType: String = checkNotNull(
        savedStateHandle[FilterDestination
            .FILTER_TYPE_ARG]
    )

    private var _imagesUiState = MutableStateFlow(ImagesUiState())
    val imagesUiState: StateFlow<ImagesUiState> = _imagesUiState.asStateFlow()

    init {

    }

    fun getPhotosByFilter(filterType: String, id: Int){
        viewModelScope.launch {

            imagesUiState.value.networkState = NetworkState.Loading

            try {
                var filteredPhotosDto: List<PhotoDto> = if (filterType == Filters.ARTIST.name){
                ArtApi.retrofitService.getPhotosByArtist(id)
            } else {
                ArtApi.retrofitService.getPhotosByCategory(id)
            }

                _imagesUiState.update {
                    currentState ->
                    currentState.copy(networkState = NetworkState.Success("Success"),
                        filteredPhotos = filteredPhotosDto)
                }

            }







        }
    }

}

data class ImagesUiState(
    var networkState: NetworkState = NetworkState.Loading, val filteredPhotos:
        List<PhotoDto> = listOf())