package com.plcoding.stockmarketapp.data.repo

import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.core.util.Resource
import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.local.StockDatabase
import com.plcoding.stockmarketapp.data.mapper.toStockListing
import com.plcoding.stockmarketapp.data.mapper.toStockListingEntity
import com.plcoding.stockmarketapp.data.remote.StockApi
import com.plcoding.stockmarketapp.domain.model.StockListing
import com.plcoding.stockmarketapp.domain.repo.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db : StockDatabase,
    val stockListingParser : CSVParser<StockListing>
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
}