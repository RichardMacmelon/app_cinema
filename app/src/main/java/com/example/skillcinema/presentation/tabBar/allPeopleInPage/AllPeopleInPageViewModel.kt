package com.example.skillcinema.presentation.tabBar.allPeopleInPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.dto.EntityPeopleDto
import com.example.skillcinema.domain.useCase.GetActorForMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllPeopleInPageViewModel  @Inject constructor(
    private val getActorForMovieUseCase: GetActorForMovieUseCase
): ViewModel() {

    private val _actorInfo = MutableStateFlow<List<EntityPeopleDto>>(emptyList())
    val actorInfo = _actorInfo.asStateFlow()

    private val _workerMovieInfo = MutableStateFlow<List<EntityPeopleDto>>(emptyList())
    val workerMovieInfo = _workerMovieInfo.asStateFlow()

    fun loadInfoPeople(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getActorForMovieUseCase.getPeopleForMovieUseCase(filmId)
            }.fold(
                onSuccess = {
                    val listActor = emptyList<EntityPeopleDto>().toMutableList()
                    val listWorker = emptyList<EntityPeopleDto>().toMutableList()
                    for (i in 0 until it.size) {
                        if (it[i].professionKey == "ACTOR") {
                            listActor.add(it[i])
                        } else {
                            listWorker.add(it[i])
                        }
                    }
                    _actorInfo.value = listActor
                    _workerMovieInfo.value = listWorker
                },
                onFailure = { Log.d("FilmPageViewModelActor", it.message ?: "") }
            )
        }
    }

}