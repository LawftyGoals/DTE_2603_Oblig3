package com.example.oblig_3.ui.image

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oblig_3.network.ArtistDto
import com.example.oblig_3.network.CategoryDto
import com.example.oblig_3.network.NetworkState
import com.example.oblig_3.network.getHttpErrorMessage
import com.example.oblig_3.ui.data.ArtRepository
import com.example.oblig_3.ui.data.PhotoSize
import com.example.oblig_3.ui.data.PurchaseItem
import com.example.oblig_3.ui.data.PurchaseItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ImagePreviewViewModel(savedStateHandle: SavedStateHandle, private val artRepository: ArtRepository, private val purchaseItemRepository: PurchaseItemRepository): ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle[ImagePreviewDestination.IMAGE_ID_ARG])

    private val _previewUiState = MutableStateFlow(PreviewUiState())
    val previewUiState: StateFlow<PreviewUiState> = _previewUiState.asStateFlow()

    init {
        getPhotoById(id)
    }


    fun getPhotoById(id: Int) {
        viewModelScope.launch {
            previewUiState.value.networkState = NetworkState.Loading

            try {
                val photoDto = artRepository.getPhotoById(id)
                val category = artRepository.getCategoryById(photoDto.categoryId)
                val artist = artRepository.getArtistById(photoDto.artistId)
                val photoForUi = PhotoForUi(photoDto.id, photoDto.title, photoDto.imageThumbUrl, photoDto.imageUrl, artist, category, photoDto.price)

                _previewUiState.update {
                    currentState ->
                    currentState.copy(networkState = NetworkState.Success("Success"),
                        photo = photoForUi)
                }

            } catch (e: IOException) {
                Log.i("filter IOExcpetion", e.toString())
                _previewUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Error(e.localizedMessage)
                    )
                }
            } catch (e: HttpException) {
                Log.i("filter HttpExcpetion", e.toString())
                val message = getHttpErrorMessage(e)
                _previewUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Error(message)
                    )
                }

            }
        }
    }

    fun addPhotoToShoppingCart(photo: PhotoForUi) {
        purchaseItemRepository.insertPurchaseItem(PurchaseItem(photoId = photo.id, photoSize = PhotoSize()))
    }


}


data class PreviewUiState(var networkState: NetworkState = NetworkState.Loading,
                          val photo: PhotoForUi? = null
)


