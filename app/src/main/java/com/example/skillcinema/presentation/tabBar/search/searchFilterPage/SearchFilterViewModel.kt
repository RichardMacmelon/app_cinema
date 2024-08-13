package com.example.skillcinema.presentation.tabBar.search.searchFilterPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFilterViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _rating = MutableSharedFlow<String>()
    val rating = _rating.asSharedFlow()

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
}