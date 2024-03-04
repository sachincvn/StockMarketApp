package com.chavan.stockmarketapp.presentation.stock_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavan.stockmarketapp.core.util.Resource
import com.chavan.stockmarketapp.domain.repo.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListingViewModel @Inject constructor(
    private val stockRepository: StockRepository
): ViewModel() {
    var state by mutableStateOf(StockListingState())

    private var searchJob : Job? = null

    init {
        getStockListing()
    }
    fun onEvent(event : StockListingEvents){
        when(event){
            is StockListingEvents.OnSearchQueryChange -> {
                state = state.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getStockListing(query = state.searchQuery)
                }
            }
            StockListingEvents.Refresh -> {
                getStockListing(fetchFromRemote = true)
            }
        }
    }

    private fun getStockListing(
        query : String = state.searchQuery.lowercase(),
        fetchFromRemote : Boolean = false
    ){
        viewModelScope.launch {
            stockRepository.getStockListings(fetchFromRemote,query)
                .collect{ result ->
                    when(result){
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                           result.data?.let { stockList ->
                               state = state.copy(
                                   stocks = stockList
                               )
                           }
                        }
                    }
                }
        }
    }
}