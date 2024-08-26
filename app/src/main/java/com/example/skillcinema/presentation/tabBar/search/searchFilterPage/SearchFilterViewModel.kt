package com.example.skillcinema.presentation.tabBar.search.searchFilterPage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.EntityIdCountriesDto
import com.example.skillcinema.data.EntityIdGenresDto
import com.example.skillcinema.domain.GetIdForCountryAndGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFilterViewModel @Inject constructor(
    application: Application,
    private val getIdForCountryAndGenreUseCase: GetIdForCountryAndGenreUseCase
) : AndroidViewModel(application) {

    private val _rating = MutableSharedFlow<String>()
    val rating = _rating.asSharedFlow()

    private val _idForGenre = MutableStateFlow<List<EntityIdGenresDto>>(emptyList())
    val idForGenre = _idForGenre.asStateFlow()

    private val _idForCountry = MutableStateFlow<List<EntityIdCountriesDto>>(emptyList())
    val idForCountry = _idForCountry.asStateFlow()

    var country: String = "USA"
    var idCountry: Int = 1
    var genre: String = ""
    var year: String = ""

    fun getRating(firstValue: Int, secondValue: Int) {
        viewModelScope.launch {
            if (firstValue == 1 && secondValue == 10) {
                _rating.emit("любой")
            } else if (firstValue == 1 && secondValue != 10) {
                _rating.emit("до $secondValue")
            } else if (firstValue != 1 && secondValue == 10) {
                _rating.emit("от $firstValue")
            } else if (firstValue == secondValue) {
                _rating.emit("только $firstValue")
            } else {
                _rating.emit("от $firstValue до $secondValue")
            }
        }
    }

    fun getIdForCountryAndGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getIdForCountryAndGenreUseCase.getIdForCountryAndGenre()
            }.fold(
                onSuccess = {
                    _idForGenre.value = it.genres
                    println(_idForGenre.value)
                    _idForCountry.value = it.countries
                    println(_idForCountry.value)
                },
                onFailure = { Log.d("SearchFilterViewModel", it.message ?: "") }
            )
        }
    }

    fun saveCountry(country: String, idCountry: Int) {
        this.country = country
        this.idCountry = idCountry
    }

    fun saveGenre(genre: String) {
        this.genre = genre
    }

    fun saveYear(yearStart: Int, yearEnd: Int) {
        if (yearEnd == yearStart) {
            year = "Только $yearEnd"
        } else if (yearEnd > yearStart) {
            year = "C $yearStart по $yearEnd"
        } else {
            year = "C $yearEnd по $yearStart"
        }
    }

    private val _isBlue = MutableLiveData(false)
    val isBlue: LiveData<Boolean> get() = _isBlue

    fun toggleColor() {
        _isBlue.value = !(_isBlue.value ?: false)
        println(_isBlue.value)
    }
}