package com.example.skillcinema.presentation.tabBar.search.searchResultPage

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.data.dto.EntityItemsMoviesForFiltersDto
import com.example.skillcinema.domain.useCase.GetFilmUsingFiltersUseCase
import javax.inject.Inject

class MyResultPagingSource @Inject constructor(
    private val getFilmUsingFiltersUseCase: GetFilmUsingFiltersUseCase,
    private val countries: Int,
    private val genres: Int,
    private val order: String,
    private val type: String,
    private val ratingFrom: Int,
    private val ratingTo: Int,
    private val yearFrom: Int,
    private val yearTo: Int
) : PagingSource<Int, EntityItemsMoviesForFiltersDto>() {

    override fun getRefreshKey(state: PagingState<Int, EntityItemsMoviesForFiltersDto>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EntityItemsMoviesForFiltersDto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            getFilmUsingFiltersUseCase.getFilmUsingFilters(countries, genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, page).items
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )

            },
            onFailure = {
                Log.d("MovieListCollections PagingSource", it.message ?: "")
                LoadResult.Error(it)
            }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}
