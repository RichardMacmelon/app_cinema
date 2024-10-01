package com.example.skillcinema.presentation.tabBar.galleryPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.data.dto.EntityItemsPhotoDto
import com.example.skillcinema.domain.useCase.GetPhotoForMovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryPageViewModel @Inject constructor(
    private val getPhotoForMovieUseCase: GetPhotoForMovieUseCase
) : ViewModel() {

    fun loadGalleryMovie(key: Int, movieId: Int?): Flow<PagingData<EntityItemsPhotoDto>> {
        val galleryMovie: Flow<PagingData<EntityItemsPhotoDto>> = Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = null,
            pagingSourceFactory = {
                MyGalleryPagePagingSource(
                    getPhotoForMovieUseCase,
                    movieId,
                    key
                )
            }
        ).flow.cachedIn(viewModelScope)
        return galleryMovie
    }
}