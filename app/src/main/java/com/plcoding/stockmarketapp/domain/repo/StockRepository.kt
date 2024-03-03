package com.plcoding.stockmarketapp.domain.repo

import com.plcoding.stockmarketapp.core.util.Resource
import com.plcoding.stockmarketapp.domain.model.StockListing
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getStockListings(
        fetchFromRemote : Boolean,
        query : String
    ) : Flow<Resource<List<StockListing>>>
}