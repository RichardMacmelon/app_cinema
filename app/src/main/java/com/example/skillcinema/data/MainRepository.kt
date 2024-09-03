package com.example.skillcinema.data

import com.example.skillcinema.entity.EntityActorInfo
import javax.inject.Inject

class MainRepository @Inject constructor() {

    suspend fun getFilmInformation(id: Int?): EntityFilmDto {
        return RetrofitService.searchPremieresMovieApi.getInfoMovie(id)
    }

    suspend fun getMovieListCollections(type: String, page: Int): List<EntityItemsDto> {
        return RetrofitService.searchPremieresMovieApi.getCollectionsMovies(type, page).items
    }

    suspend fun getSimilarsMovie(id: Int?): EntitySimilarsFilmsDto {
        return RetrofitService.searchPremieresMovieApi.getSimilarMovies(id)
    }

    suspend fun getPeopleForMovie(filmId: Int): List<EntityPeopleDto> {
        return RetrofitService.searchPremieresMovieApi.getActorForMovie(filmId)
    }

    suspend fun getPhotoForMovie(id: Int?, type: String, page: Int): EntityPhotoForFilmDto {
        return RetrofitService.searchPremieresMovieApi.getPhotoForMovie(id, type, page)
    }

    suspend fun getInfoForPerson(id: Int?): EntityActorInfoDto {
        return RetrofitService.searchPremieresMovieApi.getInfoForPerson(id)
    }

    suspend fun getSearchMovie(keyword: String, page: Int): EntitySearchMovieDto {
        return RetrofitService.searchPremieresMovieApi.getSearchMovie(keyword, page)
    }

    suspend fun getIdForCountryAndGenre(): EntityIdCountryAndGenresDto {
        return RetrofitService.searchPremieresMovieApi.getIdForCountryAndGenre()
    }

    suspend fun getFilmUsingFilters(
        countries: Int,
        genres: Int,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        page: Int
    ): EntityMoviesForFiltersDto {
        return RetrofitService.searchPremieresMovieApi.getFilmUsingFilters(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            page
        )
    }
}