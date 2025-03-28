package com.example.oblig_3.ui.data

data class ArtPurchaseUiState (
    val price: Float = 0f,

    val chosenFilter: Filters? = null,
    val chosenArtist: Artist? = null,
    val chosenCategory: Category? = null,
    val targetPhoto: Photo? = null,
    val purchaseItemList: List<PurchaseItem> = listOf()

)
