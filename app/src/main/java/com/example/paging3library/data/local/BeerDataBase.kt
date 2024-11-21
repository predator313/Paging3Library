package com.example.paging3library.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [BeerEntity::class],
    version = 1
)
abstract class BeerDataBase : RoomDatabase(){
    abstract val dao: BeerDao
}