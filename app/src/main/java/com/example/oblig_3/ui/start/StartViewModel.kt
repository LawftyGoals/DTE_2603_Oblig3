package com.example.oblig_3.ui

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oblig_3.ui.data.ArtVendorUiState
import com.example.oblig_3.ui.data.Artist
import com.example.oblig_3.ui.data.Category
import com.example.oblig_3.ui.data.Filters
import com.example.oblig_3.ui.data.Photo
import com.example.oblig_3.ui.data.PurchaseItem
import com.example.oblig_3.ui.data.PurchaseItemDto
import com.example.oblig_3.ui.data.PurchaseItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartViewModel(private val purchaseItemRepository: PurchaseItemRepository): ViewModel() {

    //ROOM REPOSITORY

    fun addToShoppingCart(purchaseItemDto: PurchaseItemDto) {
        viewModelScope.launch{
            purchaseItemRepository.insertPurchaseItem(PurchaseItem( photoId = purchaseItemDto
                .photo
                .id.toInt(), photoSize = purchaseItemDto.photoSize, frameSize = purchaseItemDto
                .frameSize, frameType = purchaseItemDto.frameType))
        }
    }

    fun removeFromShoppingCart(id: Int) {
        viewModelScope.launch {
            val purchaseItem = purchaseItemRepository.getPurchaseItemStreamById(id).filterNotNull()
                .first()
            purchaseItemRepository.deleteItem(purchaseItem)
        }
    }

    val shoppingCartState: StateFlow<ShoppingCartState> = purchaseItemRepository
        .getAllPurchaseItemsStream().map{ ShoppingCartState(it) }.stateIn( scope =
            viewModelScope, started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ShoppingCartState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ShoppingCartState(val purchaseItemList: List<PurchaseItem> = listOf())



