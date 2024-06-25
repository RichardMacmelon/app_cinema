package com.example.skillcinema.data

import javax.inject.Inject

class MainRepository @Inject constructor(){

    suspend fun getPremieres(year: Int, month: String): List<EntityItemsDto> {
        return RetrofitService.searchPremieresMovieApi.getPremieresMovie(year, month).items
    }


    suspend fun getFilmInformation(id: Int?) : EntityFilmDto {
        return RetrofitService.searchPremieresMovieApi.getInfoMovie(id)
    }

    suspend fun getMovieListCollections(type: String, page: Int) : List<EntityItemsDto> {
        return RetrofitService.searchPremieresMovieApi.getCollectionsMovies(type, page).items
    }
}