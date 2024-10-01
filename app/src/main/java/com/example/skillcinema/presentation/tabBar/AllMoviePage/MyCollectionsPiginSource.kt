package com.example.skillcinema.presentation.tabBar.AllMoviePage

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.data.dto.EntityItemsDto
import com.example.skillcinema.domain.useCase.GetCollectionsUseCase
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel.Companion.COMICS_THEME
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel.Companion.TOP_250
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel.Companion.TOP_250_TV_SHOWS
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel.Companion.TOP_POPULAR_ALL
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel.Companion.VAMPIRE_THEME
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
                    getCollectionsUseCase.getTop250Collections(COMICS_THEME, page)
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
                    getCollectionsUseCase.getTop250Collections(TOP_250_TV_SHOWS, page)
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
    }
}