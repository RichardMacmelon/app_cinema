package com.example.skillcinema.entity.entityForDB

import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.FilmDB

interface EntityCollectionWithFilms {
    val collectionDB: CollectionDB
    val filmDB: List<FilmDB>?
}