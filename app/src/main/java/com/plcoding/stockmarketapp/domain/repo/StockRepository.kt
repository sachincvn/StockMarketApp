package com.plcoding.stockmarketapp.domain.repo

import com.plcoding.stockmarketapp.core.util.Resource
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.domain.model.StockInfo
import com.plcoding.stockmarketapp.domain.model.StockListing
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getStockListings(
        fetchFromRemote : Boolean,
        query : String
    ) : Flow<Resource<List<StockListing>>>

    suspend fun getIntradayInfo(
        symbol : String
    ) : Resource<List<IntradayInfo>>

    suspend fun getStockInfo(
        symbol: String
    ) : Resource<StockInfo>
}