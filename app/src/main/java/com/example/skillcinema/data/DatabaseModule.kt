package com.example.skillcinema.data

import android.content.Context
import androidx.room.Room
import com.example.skillcinema.data.dao.CollectionDao
import com.example.skillcinema.data.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.inMemoryDatabaseBuilder(
            appContext,
            AppDataBase::class.java
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCollectionDao(db: AppDataBase): CollectionDao {
        return db.collectionDao()
    }
}
