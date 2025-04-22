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
import com.example.oblig_3.ui.data.Filters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class FilterViewModel(savedStateHandle: SavedStateHandle, private val artRepository: ArtRepository):ViewModel() {

    private var _filterUiState = MutableStateFlow(FilterUiState())
    val filterUiState: StateFlow<FilterUiState> = _filterUiState.asStateFlow()

    private val filterType: String = checkNotNull(savedStateHandle[FilterDestination
        .FILTER_TYPE_ARG])

    init {
        getCategories(filterType)
    }

    fun getCategories(filterType: String) {
        viewModelScope.launch{
            filterUiState.value.networkState = NetworkState.Loading

            try {
                _filterUiState.update {
                    currentState ->
                if (filterType == Filters.ARTIST.name) {
                    val artistList = artRepository.getAllArtists()

                    currentState.copy(networkState = NetworkState.Success("Success"), filterType =
                        filterType,
                        artistList = artistList)
                }
                else {
                    val categoryList = artRepository.getAllCategories()

                    currentState.copy(networkState = NetworkState.Success("Success"),
                        filterType = filterType, categoryList = categoryList
                    )
                }

            }
            } catch (e: IOException) {
                Log.i("filter IOExcpetion", e.toString())
                _filterUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Error(e.localizedMessage)
                    )
                }
            } catch (e: HttpException) {
                Log.i("filter HttpExcpetion", e.toString())
                val message = getHttpErrorMessage(e)
                _filterUiState.update { currentState ->
                    currentState.copy(
                        networkState = NetworkState.Error(message)
                    )
                }

            }

        }

    }

}

data class FilterUiState(
    var networkState: NetworkState = NetworkState.Loading,
    val filterType: String = "",
    val artistList: List<ArtistDto> = emptyList(),
    val categoryList : List<CategoryDto> = emptyList()
)


