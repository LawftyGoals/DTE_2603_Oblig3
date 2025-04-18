package com.example.oblig_3.ui

import androidx.lifecycle.ViewModel
import com.example.oblig_3.ui.data.ArtVendorUiState
import com.example.oblig_3.ui.data.Artist
import com.example.oblig_3.ui.data.Category
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.data.Photo
import com.example.oblig_3.ui.data.PurchaseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ArtVendorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ArtVendorUiState())
    val uiState: StateFlow<ArtVendorUiState> = _uiState.asStateFlow()

    fun updateCurrentPurchaseItem(purchaseItem: PurchaseItem){
        _uiState.update {
            currentState ->
            currentState.copy(currentPurchaseItem = purchaseItem)
        }
    }

    fun updatePurchaseItemCart(purchaseItem: PurchaseItem) {
        _uiState.update {
            currentState ->
            val purchaseItemList = currentState.purchaseItemCart.toMutableList()
            purchaseItem.id = currentState.purchaseItemIndex
            purchaseItemList.add(purchaseItem)
            currentState.copy(purchaseItemIndex = currentState.purchaseItemIndex + 1, purchaseItemCart = purchaseItemList)
        }
    }

    fun deleteFromPurchaseItemCart(purchaseItemId: Int){
        _uiState.update{
                currentState ->
            val purchaseItemList = currentState.purchaseItemCart.filter { purchaseItem -> purchaseItem.id != purchaseItemId }
            currentState.copy(purchaseItemCart = purchaseItemList)

        }
    }

    fun updateChosenFilter(filter: Filters){
        _uiState.update {
            currentState ->
            currentState.copy(
                chosenFilter = filter
            )
        }
    }

    fun resetArtistAndCategory(){
        _uiState.update {
            currentState ->
            currentState.copy(
                chosenArtist = null,
                chosenCategory =  null
            )
        }
    }

    fun <T> updateChosenArtistOrCategory(chosen: T) {
        if(uiState.value.chosenCategory != null || uiState.value.chosenArtist != null) {
            resetArtistAndCategory()
        }

        if(chosen is Artist){
            _uiState.update {
                    currentState ->
                    currentState.copy(chosenArtist = chosen)
               }
        } else if (chosen is Category){
            _uiState.update {
                currentState ->
                currentState.copy(chosenCategory = chosen)
            }
        }
    }

    fun setTargetPhoto(photo: Photo){
        _uiState.update {
                currentState ->
            currentState.copy(
                targetPhoto = photo
            )
        }
    }

}




