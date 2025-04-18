package com.example.oblig_3.ui.data

data class ArtVendorUiState (
    val purchaseItemIndex: Int = 1,

    val chosenFilter: Filters? = null,
    val chosenArtist: Artist? = null,
    val chosenCategory: Category? = null,
    val targetPhoto: Photo = testPhoto,
    val purchaseItemCart: List<PurchaseItemDto> = listOf(),
    val currentPurchaseItem: PurchaseItemDto? = null

)
