package com.example.skillcinema.presentation.tabBar.AllMoviePage

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.domain.GetCollectionsUseCase
import javax.inject.Inject


class MyCollectionsPiginSource @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val key: Int?
) : PagingSource<Int, EntityItemsDto>() {

    override fun getRefreshKey(state: PagingState<Int, EntityItemsDto>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EntityItemsDto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            when(key) {
                1 -> {
                    getCollectionsUseCase.getTop250Collections(POPULAR_SERIES, page)
                }
                2 -> {
                    getCollectionsUseCase.getTop250Collections(TOP_250, page)
                }
                3 -> {
                    getCollectionsUseCase.getTop250Collections(TOP_POPULAR_ALL, page)
                }
                4 -> {
                    getCollectionsUseCase.getTop250Collections(VAMPIRE_THEME, page)
                }
                else -> {
                    getCollectionsUseCase.getTop250Collections(FAMILY, page)
                }
            }

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
        private const val POPULAR_SERIES = "POPULAR_SERIES"
        private const val TOP_250 = "TOP_250_MOVIES"
        private const val TOP_POPULAR_ALL = "TOP_POPULAR_ALL"
        private const val VAMPIRE_THEME = "VAMPIRE_THEME"
        private const val FAMILY = "FAMILY"
    }
}