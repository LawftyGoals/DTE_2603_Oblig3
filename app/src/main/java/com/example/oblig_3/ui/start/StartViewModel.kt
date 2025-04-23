package com.example.oblig_3.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oblig_3.ui.data.PurchaseItem
import com.example.oblig_3.ui.data.PurchaseItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StartViewModel(private val purchaseItemRepository: PurchaseItemRepository): ViewModel() {

    val shoppingCartState: StateFlow<ShoppingCartState> = purchaseItemRepository
        .getAllPurchaseItemsStream().map{ ShoppingCartState(it) }.stateIn( scope =
            viewModelScope, started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ShoppingCartState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    //ROOM REPOSITORY
    fun removeFromShoppingCart(id: Int) {
        viewModelScope.launch {
            val purchaseItem = purchaseItemRepository.getPurchaseItemStreamById(id).filterNotNull()
                .first()
            purchaseItemRepository.deleteItem(purchaseItem)
        }
    }

}

data class ShoppingCartState(val purchaseItemList: List<PurchaseItem> = listOf())



