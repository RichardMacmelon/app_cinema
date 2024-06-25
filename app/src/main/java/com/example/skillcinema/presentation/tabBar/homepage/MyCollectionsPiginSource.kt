package com.example.skillcinema.presentation.tabBar.homepage

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.domain.GetCollectionsUseCase
import javax.inject.Inject


//class MyCollectionsPiginSource @Inject constructor(
//    private val getCollectionsUseCase: GetCollectionsUseCase
//) : PagingSource<Int, EntityItemsDto>() {
//
//    override fun getRefreshKey(state: PagingState<Int, EntityItemsDto>): Int = FIRST_PAGE
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EntityItemsDto> {
//        val page = params.key ?: FIRST_PAGE
//        return kotlin.runCatching {
//            getCollectionsUseCase.getCollectionsUseCase(TYPE, page).subList(0,1)
//        }.fold(
//            onSuccess = {
//                LoadResult.Page(
//                    data = it,
//                    prevKey = null,
//                    nextKey = if (it.isEmpty()) null else page + 1
//                )
//
//            },
//            onFailure = {
//                Log.d("MovieListCollections PagingSource", it.message ?: "")
//                LoadResult.Error(it)
//            }
//        )
//    }
//
//
//    companion object {
//        const val FIRST_PAGE = 1
//        const val TYPE = "TOP_250_MOVIES"
//    }
//}