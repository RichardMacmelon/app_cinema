package com.example.skillcinema.presentation.tabBar.filmpage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MyParcelableFilmClass(
    val filmId: Int,
    val filmName: String,
    val filmYear: String,
    val filmGenre: String,
    val filmPoster: String,
    val filmRating: String
) : Parcelable
