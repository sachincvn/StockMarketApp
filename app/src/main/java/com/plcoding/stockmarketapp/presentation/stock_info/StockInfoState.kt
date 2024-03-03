package com.plcoding.stockmarketapp.presentation.stock_info

import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.domain.model.StockInfo

data class StockInfoState(
    val intradayInfo: List<IntradayInfo> = emptyList(),
    val stockInfo: StockInfo? = null,
    val isLoading : Boolean = false,
    val error : String? = null
)