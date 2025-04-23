package com.example.oblig_3.ui.image

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oblig_3.network.ArtistDto
import com.example.oblig_3.network.CategoryDto
import com.example.oblig_3.network.FrameSizeDto
import com.example.oblig_3.network.FrameTypeDto
import com.example.oblig_3.network.ImageDto
import com.example.oblig_3.network.NetworkState
import com.example.oblig_3.network.ImageSizeDto
import com.example.oblig_3.network.getHttpErrorMessage
import com.example.oblig_3.ui.data.ArtRepository
import com.example.oblig_3.ui.data.PurchaseItem
import com.example.oblig_3.ui.data.PurchaseItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ImagePreviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val artRepository: ArtRepository,
    private val purchaseItemRepository: PurchaseItemRepository
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle[ImagePreviewDestination.IMAGE_ID_ARG])

    private val _previewUiState = MutableStateFlow(PreviewUiState())
    val previewUiState: StateFlow<PreviewUiState> = _previewUiState.asStateFlow()

    init {
        getImageById(id)
    }


    fun getImageById(id: Int) {
        viewModelScope.launch {
            previewUiState.value.networkState = NetworkState.Loading

            try {
                val imageDto = artRepository.getImageById(id)
                val category = artRepository.getCategoryById(imageDto.categoryId)
                val artist = artRepository.getArtistById(imageDto.artistId)

                val imageSizes = artRepository.getAllImageSizes()
                val frameTypes = artRepository.getAllFrameTypes()
                val frameSizes = artRepository.getAllFrameSizes()

                val imageForUi = ImageForUi(
                    imageDto.id,
                    imageDto.title,
                    imageDto.imageThumbUrl,
                    imageDto.imageUrl,
                    artist,
                    category,
                    imageDto.price
                )

                _previewUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Success("Success"),
                        image = imageForUi,
                        imageSizes = imageSizes,
                        frameTypes = frameTypes,
                        frameSizes = frameSizes,
                        currentPurchaseItem = purchaseItemDtoFromComponents(
                            imageDto,
                            artist,
                            category,
                            imageSizes[0],
                            frameTypes[0],
                            frameSizes[0]
                        )
                    )
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

    fun updateCurrentPurchaseItem(purchaseItemDto: PurchaseItemDto){
        _previewUiState.update {
            currentState ->
            currentState.copy(currentPurchaseItem = purchaseItemDto)
        }
        
    }

    fun addImageToShoppingCart() {
        viewModelScope.launch {
            _previewUiState.value.currentPurchaseItem?.let { purchaseItemDto ->
                purchaseItemRepository.insertPurchaseItem(
                    purchaseItemFromDto(purchaseItemDto)
                )
            }
        }

    }


}


data class PreviewUiState(
    var networkState: NetworkState = NetworkState.Loading,
    val image: ImageForUi? = null,
    val imageSizes: List<ImageSizeDto> = emptyList(),
    val frameTypes: List<FrameTypeDto> = emptyList(),
    val frameSizes: List<FrameSizeDto> = emptyList(),
    val currentPurchaseItem: PurchaseItemDto? = null
)


data class PurchaseItemDto(
    val image: ImageForUi,
    var imageSize: ImageSizeDto,
    var frameType: FrameTypeDto,
    var frameSize: FrameSizeDto
)


fun purchaseItemDtoFromComponents(
    imageDto: ImageDto,
    artist: ArtistDto,
    category: CategoryDto,
    imageSize: ImageSizeDto,
    frameType: FrameTypeDto,
    frameSize: FrameSizeDto
): PurchaseItemDto {
    return PurchaseItemDto(
        image = ImageForUi(
            imageDto.id,
            imageDto.title,
            imageDto.imageThumbUrl,
            imageDto.imageUrl,
            artist,
            category,
            imageDto.price
        ), imageSize = imageSize, frameType = frameType, frameSize = frameSize
    )
}

fun purchaseItemFromDto(purchaseItemDto: PurchaseItemDto): PurchaseItem {
    return PurchaseItem(
        imageId = purchaseItemDto.image.id,
        imageTitle = purchaseItemDto.image.title,
        imageThumbUrl = purchaseItemDto.image.imageThumbUrl,
        imageUrl = purchaseItemDto.image.imageUrl,
        artistId = purchaseItemDto.image.artist.id,
        artistFirstName = purchaseItemDto.image.artist.firstName,
        artistLastName = purchaseItemDto.image.artist.lastName,
        categoryId = purchaseItemDto.image.category.id,
        categoryName = purchaseItemDto.image.category.name,
        price = purchaseItemDto.image.price,
        imageSizeId = purchaseItemDto.imageSize.id,
        imageSizeName = purchaseItemDto.imageSize.name,
        imageSizeValue = purchaseItemDto.imageSize.size,
        imageSizeExtraPrice = purchaseItemDto.imageSize.extraPrice,
        frameSizeId = purchaseItemDto.frameSize.id,
        frameSizeName = purchaseItemDto.frameSize.name,
        frameSizeValue = purchaseItemDto.frameSize.size,
        frameSizeExtraPrice = purchaseItemDto.frameSize.extraPrice,
        frameTypeId = purchaseItemDto.frameType.id,
        frameTypeName = purchaseItemDto.frameType.name,
        frameTypeValue = purchaseItemDto.frameType.color,
        frameTypeExtraPrice = purchaseItemDto.frameType.extraPrice
    )
}

fun dtoFromPurchaseItem(purchaseItem: PurchaseItem): PurchaseItemDto {
    return PurchaseItemDto(
        image = ImageForUi(
            purchaseItem.imageId,
            title = purchaseItem.imageTitle,
            imageThumbUrl = purchaseItem.imageThumbUrl,
            imageUrl = purchaseItem.imageUrl,
            artist = ArtistDto(
                purchaseItem.artistId,
                purchaseItem.artistFirstName,
                purchaseItem.artistLastName
            ),
            category = CategoryDto(purchaseItem.categoryId, purchaseItem.categoryName),
            price = purchaseItem.price
        ),
        imageSize = ImageSizeDto(
            purchaseItem.imageSizeId,
            purchaseItem.imageSizeName,
            purchaseItem.imageSizeValue,
            purchaseItem.imageSizeExtraPrice
        ),
        frameType = FrameTypeDto(
            purchaseItem.frameTypeId,
            purchaseItem.frameTypeName,
            purchaseItem.frameTypeValue,
            purchaseItem.frameTypeExtraPrice
        ),
        frameSize = FrameSizeDto(
            purchaseItem.frameSizeId,
            purchaseItem.frameSizeName,
            purchaseItem.frameSizeValue,
            purchaseItem.frameSizeExtraPrice
        )
    )
}