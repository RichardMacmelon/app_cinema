package com.example.skillcinema.presentation.tabBar.search.searchFilterPage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.data.EntityIdCountriesDto
import com.example.skillcinema.data.EntityIdGenresDto
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.data.EntityItemsMoviesForFiltersDto
import com.example.skillcinema.data.EntityMoviesForFiltersDto
import com.example.skillcinema.domain.GetFilmUsingFiltersUseCase
import com.example.skillcinema.domain.GetIdForCountryAndGenreUseCase
import com.example.skillcinema.presentation.tabBar.AllMoviePage.MyCollectionsPiginSource
import com.example.skillcinema.presentation.tabBar.search.searchResultPage.MyResultPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFilterViewModel @Inject constructor(
    application: Application,
    private val getIdForCountryAndGenreUseCase: GetIdForCountryAndGenreUseCase,
    private val getFilmUsingFiltersUseCase: GetFilmUsingFiltersUseCase
) : AndroidViewModel(application) {

    private val _rating = MutableSharedFlow<String>()
    val rating = _rating.asSharedFlow()

    private val _idAndGenre = MutableStateFlow<List<EntityIdGenresDto>>(emptyList())
    val idAndGenre = _idAndGenre.asStateFlow()

    private val _idAndCountry = MutableStateFlow<List<EntityIdCountriesDto>>(emptyList())
    val idAndCountry = _idAndCountry.asStateFlow()

    var country: String = ""
    var genre: String = ""
    var year: String = ""

    private var idCountry: Int = 0
    private var idGenre: Int = 0
    private var startYear: Int = 0
    private var endYear: Int = 0
    private var startRating: Int = 0
    private var endRating: Int = 10
    private var radioButtonFilmSerial: String = "ALL"
    private var radioButtonDataPopularRating: String = "YEAR"

    fun getFilerMovie() :  Flow<PagingData<EntityItemsMoviesForFiltersDto>>{
         val pagedMovie: Flow<PagingData<EntityItemsMoviesForFiltersDto>> = Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = null,
            pagingSourceFactory = {
                MyResultPagingSource(
                    getFilmUsingFiltersUseCase,
                    idCountry,
                    idGenre,
                    radioButtonDataPopularRating,
                    radioButtonFilmSerial,
                    startRating,
                    endRating,
                    startYear,
                    endYear
                )
            }
        ).flow.cachedIn(viewModelScope)
        return pagedMovie
    }



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
            startRating = firstValue
            endRating = secondValue
        }
    }

    fun getIdForCountryAndGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getIdForCountryAndGenreUseCase.getIdForCountryAndGenre()
            }.fold(
                onSuccess = {
                    _idAndGenre.value = it.genres
                    _idAndCountry.value = it.countries
                },
                onFailure = { Log.d("SearchFilterViewModel", it.message ?: "") }
            )
        }
    }

    fun saveCheckedRadioButtonFilmsSerials(radioButtonFilmSerial: String) {
        this.radioButtonFilmSerial = radioButtonFilmSerial
        println(this.radioButtonFilmSerial)
    }

    fun saveCheckedRadioButtonDataPopularRating(radioButtonDataPopularRating: String) {
        this.radioButtonDataPopularRating = radioButtonDataPopularRating
        println(this.radioButtonDataPopularRating)
    }

    fun saveCountry(country: String, idCountry: Int) {
        this.country = country
        this.idCountry = idCountry
    }

    fun saveGenre(genre: String, idGenre: Int) {
        this.genre = genre
        this.idGenre = idGenre
    }

    fun saveYear(yearStart: Int, yearEnd: Int) {
        if (yearEnd == yearStart) {
            startYear = yearStart
            endYear = yearEnd
            year = "Только $yearEnd"
        } else if (yearEnd > yearStart) {
            startYear = yearStart
            endYear = startYear
            year = "C $yearStart по $yearEnd"
        } else {
            startYear = yearEnd
            endYear = yearStart
            year = "C $yearEnd по $yearStart"
        }
    }

    private val _isBlue = MutableLiveData(false)
    val isBlue: LiveData<Boolean> get() = _isBlue

    fun toggleColor() {
        _isBlue.value = !(_isBlue.value ?: false)
    }
}