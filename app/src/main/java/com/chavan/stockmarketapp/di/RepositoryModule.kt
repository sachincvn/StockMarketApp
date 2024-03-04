package com.chavan.stockmarketapp.di

import com.chavan.stockmarketapp.data.csv.CSVParser
import com.chavan.stockmarketapp.data.csv.IntradayInfoParser
import com.chavan.stockmarketapp.data.csv.StockListingParser
import com.chavan.stockmarketapp.data.repo.StockRepositoryImpl
import com.chavan.stockmarketapp.domain.model.IntradayInfo
import com.chavan.stockmarketapp.domain.model.StockListing
import com.chavan.stockmarketapp.domain.repo.StockRepository
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
    abstract fun bindsIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ) : CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl : StockRepositoryImpl
    ) : StockRepository
}