package com.plcoding.stockmarketapp.presentation.stock_list

import com.plcoding.stockmarketapp.domain.model.StockListing

data class StockListingState(
    val stocks : List<StockListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery : String = ""
)
