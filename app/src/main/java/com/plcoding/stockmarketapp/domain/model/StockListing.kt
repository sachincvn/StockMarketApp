package com.plcoding.stockmarketapp.domain.model

import androidx.room.PrimaryKey

data class StockListing(
    val name : String,
    val symbol : String,
    val exchange :String,
    val id : Int? = null
)