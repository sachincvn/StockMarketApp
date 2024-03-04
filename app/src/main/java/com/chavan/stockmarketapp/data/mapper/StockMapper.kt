package com.chavan.stockmarketapp.data.mapper

import com.chavan.stockmarketapp.data.local.StockListingEntity
import com.chavan.stockmarketapp.data.remote.dto.StockInfoDto
import com.chavan.stockmarketapp.domain.model.StockInfo
import com.chavan.stockmarketapp.domain.model.StockListing

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