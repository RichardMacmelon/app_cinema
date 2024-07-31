package com.example.skillcinema.presentation.tabBar.AllMoviePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.domain.GetCollectionsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllMovieViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    fun loadPagedMovie(key: Int?) : Flow<PagingData<EntityItemsDto>>  {
        val pagedMovie : Flow<PagingData<EntityItemsDto>> = Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = null,
            pagingSourceFactory = { MyCollectionsPiginSource(getCollectionsUseCase, key) }
        ).flow.cachedIn(viewModelScope)
        return pagedMovie
    }
}