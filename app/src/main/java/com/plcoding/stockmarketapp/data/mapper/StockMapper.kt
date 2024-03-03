package com.plcoding.stockmarketapp.data.mapper

import com.plcoding.stockmarketapp.data.local.StockListingEntity
import com.plcoding.stockmarketapp.data.remote.dto.StockInfoDto
import com.plcoding.stockmarketapp.domain.model.StockInfo
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

fun StockInfoDto.toStockInfo() : StockInfo {
    return StockInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
    )
}