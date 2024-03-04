package com.chavan.stockmarketapp.domain.repo

import com.chavan.stockmarketapp.core.util.Resource
import com.chavan.stockmarketapp.domain.model.IntradayInfo
import com.chavan.stockmarketapp.domain.model.StockInfo
import com.chavan.stockmarketapp.domain.model.StockListing
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