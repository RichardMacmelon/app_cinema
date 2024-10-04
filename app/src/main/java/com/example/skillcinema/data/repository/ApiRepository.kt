package com.example.skillcinema.data.repository

import com.example.skillcinema.data.api.RetrofitService
import com.example.skillcinema.data.dto.EntityActorInfoDto
import com.example.skillcinema.data.dto.EntityFilmDto
import com.example.skillcinema.data.dto.EntityIdCountryAndGenresDto
import com.example.skillcinema.data.dto.EntityItemsDto
import com.example.skillcinema.data.dto.EntityMoviesForFiltersDto
import com.example.skillcinema.data.dto.EntityPeopleDto
import com.example.skillcinema.data.dto.EntityPhotoForFilmDto
import com.example.skillcinema.data.dto.EntitySearchMovieDto
import com.example.skillcinema.data.dto.EntitySimilarsFilmsDto
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getFilmInformation(id: Int?): EntityFilmDto {
        return mainRepository.getApiInfo().getInfoMovie(id)
    }

    suspend fun getMovieListCollections(type: String, page: Int): List<EntityItemsDto> {
        return mainRepository.getApiInfo().getCollectionsMovies(type, page).items
    }

    suspend fun getSimilarsMovie(id: Int?): EntitySimilarsFilmsDto {
        return mainRepository.getApiInfo().getSimilarMovies(id)
    }

    suspend fun getPeopleForMovie(filmId: Int): List<EntityPeopleDto> {
        return mainRepository.getApiInfo().getActorForMovie(filmId)
    }

    suspend fun getPhotoForMovie(id: Int?, type: String, page: Int): EntityPhotoForFilmDto {
        return mainRepository.getApiInfo().getPhotoForMovie(id, type, page)
    }

    suspend fun getInfoForPerson(id: Int?): EntityActorInfoDto {
        return mainRepository.getApiInfo().getInfoForPerson(id)
    }

    suspend fun getSearchMovie(keyword: String, page: Int): EntitySearchMovieDto {
        return mainRepository.getApiInfo().getSearchMovie(keyword, page)
    }

    suspend fun getIdForCountryAndGenre(): EntityIdCountryAndGenresDto {
        return mainRepository.getApiInfo().getIdForCountryAndGenre()
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
        return mainRepository.getApiInfo().getFilmUsingFilters(
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