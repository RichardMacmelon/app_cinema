package com.example.skillcinema.data.api

import com.example.skillcinema.data.dto.EntityActorInfoDto
import com.example.skillcinema.data.dto.EntityCollectionsMoviesDto
import com.example.skillcinema.data.dto.EntityFilmDto
import com.example.skillcinema.data.dto.EntityIdCountryAndGenresDto
import com.example.skillcinema.data.dto.EntityMoviesForFiltersDto
import com.example.skillcinema.data.dto.EntityPeopleDto
import com.example.skillcinema.data.dto.EntityPhotoForFilmDto
import com.example.skillcinema.data.dto.EntitySearchMovieDto
import com.example.skillcinema.data.dto.EntitySimilarsFilmsDto
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

object RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchPremieresMovieApi: SearchPremieresMovieApi =
        retrofit.create(SearchPremieresMovieApi::class.java)
}

interface SearchPremieresMovieApi {

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v2.2/films/collections")
    suspend fun getCollectionsMovies(
        @Query("type") type: String,
        @Query("page") page: Int
    ): EntityCollectionsMoviesDto

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v2.2/films/{id}")
    suspend fun getInfoMovie(
        @Path("id") id: Int?
    ): EntityFilmDto

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v2.2/films/{id}/similars")
    suspend fun getSimilarMovies(
        @Path("id") id: Int?
    ): EntitySimilarsFilmsDto

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v1/staff")
    suspend fun getActorForMovie(
        @Query("filmId") filmId: Int
    ): List<EntityPeopleDto>

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v2.2/films/{id}/images")
    suspend fun getPhotoForMovie(
        @Path("id") id: Int?,
        @Query("type") type: String,
        @Query("page") page: Int
    ): EntityPhotoForFilmDto

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v1/staff/{id}")
    suspend fun getInfoForPerson(
        @Path("id") id: Int?
    ): EntityActorInfoDto

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v2.1/films/search-by-keyword")
    suspend fun getSearchMovie(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): EntitySearchMovieDto

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v2.2/films/filters")
    suspend fun getIdForCountryAndGenre(): EntityIdCountryAndGenresDto

    @Headers("X-API-KEY: $API_KEY1")
    @GET(value = "/api/v2.2/films")
    suspend fun getFilmUsingFilters(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("page") page: Int
    ): EntityMoviesForFiltersDto

    companion object {
//        const val API_KEY1 = "e908af57-9ec8-446c-8ef8-b8943c611b96"
        const val API_KEY1 = "624f507c-2e3e-4006-acdf-d7953a504c61"
    }
}


