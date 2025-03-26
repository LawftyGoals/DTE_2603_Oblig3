package com.example.oblig_3.ui

import androidx.lifecycle.ViewModel
import com.example.oblig_3.ui.data.ArtPurchaseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArtVendorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ArtPurchaseUiState())
    val uiState: StateFlow<ArtPurchaseUiState> = _uiState.asStateFlow()




}