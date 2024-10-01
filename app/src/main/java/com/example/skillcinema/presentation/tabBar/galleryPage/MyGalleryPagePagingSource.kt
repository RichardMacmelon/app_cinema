package com.example.skillcinema.presentation.tabBar.galleryPage

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.data.dto.EntityItemsPhotoDto
import com.example.skillcinema.domain.useCase.GetPhotoForMovieUseCase
import javax.inject.Inject

class MyGalleryPagePagingSource @Inject constructor(
    private val getPhotoForMovieUseCase: GetPhotoForMovieUseCase,
    private val movieId: Int?,
    private val key: Int
) : PagingSource<Int, EntityItemsPhotoDto>() {

    override fun getRefreshKey(state: PagingState<Int, EntityItemsPhotoDto>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EntityItemsPhotoDto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            when (key) {
                1 -> {
                    getPhotoForMovieUseCase.getPhotoForMovieUseCase(movieId, SCREENSHOT, page).items
                }

                2 -> {
                    getPhotoForMovieUseCase.getPhotoForMovieUseCase(movieId, SHOOTING, page).items
                }

                else -> {
                    getPhotoForMovieUseCase.getPhotoForMovieUseCase(movieId, POSTER, page).items
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
                Log.d("MyGalleryPagePagingSource", it.message ?: "")
                LoadResult.Error(it)
            }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val SCREENSHOT = "SCREENSHOT"
        private const val SHOOTING = "SHOOTING"
        private const val POSTER = "POSTER"
    }

}