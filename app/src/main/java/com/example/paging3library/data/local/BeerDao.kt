package com.example.paging3library.data.local

import androidx.paging.PagingSource
import retrofit2.http.Query

interface BeerDao {
    suspend fun upsertAll(beers: List<BeerEntity>)
    @androidx.room.Query("Select * from beerEntity")
    fun pagingSource(): PagingSource<Int, BeerEntity>
    @androidx.room.Query("Delete from beerentity")
    suspend fun clearAll()
}