package com.chavan.stockmarketapp.data.repo

import com.chavan.stockmarketapp.core.util.Resource
import com.chavan.stockmarketapp.data.csv.CSVParser
import com.chavan.stockmarketapp.data.local.StockDatabase
import com.chavan.stockmarketapp.data.mapper.toStockInfo
import com.chavan.stockmarketapp.data.mapper.toStockListing
import com.chavan.stockmarketapp.data.mapper.toStockListingEntity
import com.chavan.stockmarketapp.data.remote.StockApi
import com.chavan.stockmarketapp.domain.model.IntradayInfo
import com.chavan.stockmarketapp.domain.model.StockInfo
import com.chavan.stockmarketapp.domain.model.StockListing
import com.chavan.stockmarketapp.domain.repo.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db : StockDatabase,
    private val stockListingParser : CSVParser<StockListing>,
    private val intradayInfoParser : CSVParser<IntradayInfo>,
) : StockRepository {

    private val dao = db.dao
    override suspend fun getStockListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<StockListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchStockListing(query)
            emit(Resource.Success<List<StockListing>>(
                data = localListing.map { it.toStockListing() }
            ))

            val isCacheEmpty = localListing.isEmpty() && query.isBlank()
            val loadFromCache = !isCacheEmpty && !fetchFromRemote
            if (loadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try {
                val response = api.getListings()
                stockListingParser.parse(response.byteStream())
            }
            catch (ex : IOException){
                ex.printStackTrace()
                emit(Resource.Error("Could not load data - ${ex.message}"))
                null
            }
            catch (ex : HttpException){
                ex.printStackTrace()
                emit(Resource.Error("Could not load data - ${ex.message}"))
                null
            }
            remoteListing?.let { listing ->
                dao.clearStockListings()
                dao.insertStockListings(listing.map { it.toStockListingEntity() })
                emit(Resource.Success(
                    data = dao.searchStockListing("")
                        .map { it.toStockListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val  result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        }
        catch (ex : IOException){
            ex.printStackTrace()
            Resource.Error("Something went wrong + ${ex.message}")
        }
        catch (ex : HttpException){
            ex.printStackTrace()
            Resource.Error("Something went wrong + ${ex.message}")
        }
    }

    override suspend fun getStockInfo(symbol: String): Resource<StockInfo> {
        return try {
            val result = api.getStockInfo(symbol)
            Resource.Success(result.toStockInfo())
        }
        catch (ex : IOException){
            ex.printStackTrace()
            Resource.Error("Something went wrong + ${ex.message}")
        }
        catch (ex : HttpException){
            ex.printStackTrace()
            Resource.Error("Something went wrong + ${ex.message}")
        }
    }
}