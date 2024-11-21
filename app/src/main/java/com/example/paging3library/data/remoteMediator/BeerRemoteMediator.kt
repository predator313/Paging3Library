package com.example.paging3library.data.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3library.data.local.BeerDataBase
import com.example.paging3library.data.local.BeerEntity
import com.example.paging3library.data.remote.BeerApi
import com.example.paging3library.mapper.toBeerEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDb: BeerDataBase,
    private val beerApi: BeerApi
): RemoteMediator<Int,BeerEntity>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH ->{
                    1
                }
                LoadType.APPEND ->{
                    val lastItem = state.lastItemOrNull()
                    lastItem?.let {
                        ( it.id/state.config.pageSize)+1
                    }?:1
                }
                LoadType.PREPEND ->{
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val beers = beerApi.getBeers(
                page = loadKey,
                pageCount = state.config.pageSize
            )
            beerDb.withTransaction {
                //all success or all fail
                if(loadType == LoadType.REFRESH){
                    beerDb.dao.clearAll()
                }
                val beerEntities = beers.map {
                    it.toBeerEntity()
                }
                beerDb.dao.upsertAll(beerEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = beers.isEmpty()
            )
        }catch (e:IOException) {
            MediatorResult.Error(e)
        }catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}