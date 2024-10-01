package com.example.skillcinema.data.repository

import com.example.skillcinema.data.api.RetrofitService
import com.example.skillcinema.data.api.SearchPremieresMovieApi
import com.example.skillcinema.data.dao.CollectionDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val collectionDao: CollectionDao) {

    fun getApiInfo(): SearchPremieresMovieApi {
        return RetrofitService.searchPremieresMovieApi
    }

    fun getDBInfo(): CollectionDao {
        return collectionDao
    }
}