package com.example.oblig_3.ui.image

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oblig_3.network.ArtistDto
import com.example.oblig_3.network.CategoryDto
import com.example.oblig_3.network.NetworkState
import com.example.oblig_3.network.PhotoDto
import com.example.oblig_3.network.getHttpErrorMessage
import com.example.oblig_3.ui.data.ArtRepository
import com.example.oblig_3.ui.data.Filters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ImagesViewModel(
    savedStateHandle: SavedStateHandle,
    private val artRepository: ArtRepository
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle[ImagesDestination.ID_ARG])
    private val filterType: String = checkNotNull(
        savedStateHandle[FilterDestination
            .FILTER_TYPE_ARG]
    )

    private var _imagesUiState = MutableStateFlow(ImagesUiState())
    val imagesUiState: StateFlow<ImagesUiState> = _imagesUiState.asStateFlow()

    init {
        getPhotosByFilter(filterType, id)

    }

    fun getPhotosByFilter(filterType: String, id: Int) {
        viewModelScope.launch {

            imagesUiState.value.networkState = NetworkState.Loading

            try {
                val filteredPhotosDto: List<PhotoDto> = if (filterType == Filters.ARTIST.name) {
                    artRepository.getPhotosByArtist(id)
                } else {
                    artRepository.getPhotosByCategory(id)
                }

                val artists = artRepository.getAllArtists()
                val categories = artRepository.getAllCategories()

                _imagesUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Success("Success"),
                        filteredPhotos = filteredPhotosDto.map { photoDto ->
                            PhotoForUi(
                                photoDto.id,
                                photoDto.title,
                                photoDto.imageThumbUrl,
                                photoDto.imageUrl,
                                artists.find { artist -> artist.id == photoDto.artistId },
                                categories.find { category -> category.id == photoDto.categoryId },
                                photoDto.price
                            )
                        }
                    )
                }

            } catch (e: IOException) {
                Log.i("filter IOExcpetion", e.toString())
                _imagesUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Error(e.localizedMessage)
                    )
                }
            } catch (e: HttpException) {
                Log.i("filter HttpExcpetion", e.toString())
                val message = getHttpErrorMessage(e)
                _imagesUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Error(message)
                    )
                }

            }

        }

    }

}

data class ImagesUiState(
    var networkState: NetworkState = NetworkState.Loading, val filteredPhotos:
    List<PhotoForUi> = listOf()
)

data class PhotoForUi(
    val id: Int, val title: String,
    val imageThumbUrl: String,
    val imageUrl: String,
    val artist: ArtistDto?,
    val category: CategoryDto?,
    val price: Float
)
