package com.plcoding.stockmarketapp.data.mapper

import com.plcoding.stockmarketapp.data.local.StockListingEntity
import com.plcoding.stockmarketapp.domain.model.StockListing

fun StockListingEntity.toStockListing() : StockListing{
    return StockListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun StockListing.toStockListingEntity() : StockListingEntity{
    return StockListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}