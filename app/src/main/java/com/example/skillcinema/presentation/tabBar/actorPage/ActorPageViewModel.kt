package com.example.skillcinema.presentation.tabBar.actorPage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.EntityActorInfoDto
import com.example.skillcinema.data.EntityFilmographyDto
import com.example.skillcinema.domain.GetInfoForPersonUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActorPageViewModel @Inject constructor(
    private val getInfoForPersonUseCase: GetInfoForPersonUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _actorInfo = MutableSharedFlow<EntityActorInfoDto>()
    val actorInfo = _actorInfo.asSharedFlow()

    private val _filmographyActor = MutableStateFlow<List<EntityFilmographyDto>>(emptyList())
    val filmographyActor = _filmographyActor.asStateFlow()

    fun getActorInfo(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getInfoForPersonUseCase.getInfoForPerson(id)
            }.fold(
                onSuccess = {
                    _actorInfo.emit(it)
                },
                onFailure = {
                    Log.d("ActorPageViewModel", it.message ?: "")
                }
            )
        }
    }

    fun getFilmography(key: Int, id: Int?)  {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getInfoForPersonUseCase.getInfoForPerson(id).films
            }.fold(
                onSuccess = {
                    val listActor = emptyList<EntityFilmographyDto>().toMutableList()
                    val listActorProducer = emptyList<EntityFilmographyDto>().toMutableList()
                    val listActorHerself = emptyList<EntityFilmographyDto>().toMutableList()
                    when (key) {
                        1 -> {
                            for (i in 0 until it.size) {
                                if (it[i].professionKey == "ACTOR")
                                    listActor.add(it[i])
                            }
                            _filmographyActor.value = listActor
                            println("listActor = $listActor")
                        }

                        2 -> {
                            for (i in 0 until it.size) {
                                if (it[i].professionKey == "HIMSELF")
                                    listActorHerself.add(it[i])
                            }
                            _filmographyActor.value = listActorHerself
                            println("listActorHerself = $listActorHerself")
                        }

                        else -> {
                            for (i in 0 until it.size) {
                                if (it[i].professionKey == "PRODUCER")
                                    listActorHerself.add(it[i])
                            }
                            _filmographyActor.value = listActorProducer
                            println("listActorProducer = $listActorProducer")
                        }
                    }
                },
                onFailure = {
                    Log.d("ActorPageViewModel", it.message ?: "")
                }
            )
        }
    }
}