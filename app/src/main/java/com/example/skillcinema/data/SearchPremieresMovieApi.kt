package com.example.skillcinema.data

import com.example.skillcinema.entity.EntityCollectionsMovies
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

    @Headers("X-API-KEY: $API_KEY2")
    @GET(value = "/api/v2.2/films/premieres")
    suspend fun getPremieresMovie(
        @Query("year") year: Int,
        @Query("month") month: String
    ): EntityCollectionsMoviesDto

    @Headers("X-API-KEY: $API_KEY2")
    @GET(value = "/api/v2.2/films/collections")
    suspend fun getCollectionsMovies(
        @Query("type") type: String,
        @Query("page") page: Int
    ) : EntityCollectionsMoviesDto

    @Headers("X-API-KEY: $API_KEY2")
    @GET(value = "/api/v2.2/films/{id}")
    suspend fun getInfoMovie(
        @Path("id") id: Int?
    ): EntityFilmDto



    companion object {
        const val API_KEY1 = "e908af57-9ec8-446c-8ef8-b8943c611b96"
        const val API_KEY2 = "624f507c-2e3e-4006-acdf-d7953a504c61"
    }
}


