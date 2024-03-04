package com.chavan.stockmarketapp.domain.model

data class StockListing(
    val name : String,
    val symbol : String,
    val exchange :String,
    val id : Int? = null
)