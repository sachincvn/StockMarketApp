package com.chavan.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StockListingEntity::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao : StockDao
}