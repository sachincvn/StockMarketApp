package com.chavan.stockmarketapp.presentation.stock_info

import com.chavan.stockmarketapp.domain.model.IntradayInfo
import com.chavan.stockmarketapp.domain.model.StockInfo

data class StockInfoState(
    val intradayInfo: List<IntradayInfo> = emptyList(),
    val stockInfo: StockInfo? = null,
    val isLoading : Boolean = false,
    val error : String? = null
)