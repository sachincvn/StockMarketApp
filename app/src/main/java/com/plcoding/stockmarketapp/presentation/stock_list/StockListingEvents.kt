package com.plcoding.stockmarketapp.presentation.stock_list

sealed class StockListingEvents {
    object Refresh : StockListingEvents()
    data class OnSearchQueryChange(val query: String) : StockListingEvents()
}