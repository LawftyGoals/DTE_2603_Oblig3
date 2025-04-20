package com.example.oblig_3.ui.image

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import com.example.oblig_3.ui.data.Artist
import com.example.oblig_3.ui.data.Category
import com.example.oblig_3.ui.data.DataSource.artists
import com.example.oblig_3.ui.data.Filters

class FilterViewModel(savedStateHandle: SavedStateHandle):ViewModel() {

    var filterUiState by mutableStateOf(FilterUiState())

    private val filterType: String = checkNotNull(savedStateHandle[FilterDestination
        .FILTER_TYPE_ARG])

    init {
        filterUiState = if (filterType == Filters.ARTIST.name)
            FilterUiState(filterType = filterType, artistList = artists)
        else
            FilterUiState(filterType = filterType, categoryList = Category.entries
                .toList())
    }


}

data class FilterUiState(
    val filterType: String = "",
    val artistList: List<Artist> = listOf(),
    val categoryList : List<Category> = listOf()
)