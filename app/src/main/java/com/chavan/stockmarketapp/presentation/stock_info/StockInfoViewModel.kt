package com.chavan.stockmarketapp.presentation.stock_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavan.stockmarketapp.core.util.Resource
import com.chavan.stockmarketapp.domain.repo.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stockRepository : StockRepository
) : ViewModel() {
    var state by mutableStateOf(StockInfoState())
    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val stockInfoResult = async {stockRepository.getStockInfo(symbol) }
            val intradayInfoResult = async {stockRepository.getIntradayInfo(symbol) }
            when(val result = stockInfoResult.await()){
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        stockInfo = null
                    )
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    state = state.copy(
                        stockInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }

            when(val result = intradayInfoResult.await()){
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message,
                        intradayInfo = emptyList()
                    )
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    state = state.copy(
                        intradayInfo = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }
}