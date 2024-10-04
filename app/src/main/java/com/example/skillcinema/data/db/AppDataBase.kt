package com.example.skillcinema.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skillcinema.data.dao.CollectionDao
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.FilmDB

@Database(entities = [CollectionDB::class, FilmDB::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun collectionDao(): CollectionDao
}