package com.plcoding.stockmarketapp.di

import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.csv.StockListingParser
import com.plcoding.stockmarketapp.data.repo.StockRepositoryImpl
import com.plcoding.stockmarketapp.domain.model.StockListing
import com.plcoding.stockmarketapp.domain.repo.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsStockListingParser(
        stockListingParser: StockListingParser
    ) : CSVParser<StockListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl : StockRepositoryImpl
    ) : StockRepository
}