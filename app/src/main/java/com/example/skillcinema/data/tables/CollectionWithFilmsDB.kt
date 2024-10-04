package com.example.skillcinema.data.tables

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skillcinema.entity.entityForDB.EntityCollectionWithFilms

data class CollectionWithFilmsDB(
    @Embedded
    override val collectionDB: CollectionDB,
    @Relation(
        parentColumn = "collection_id",
        entityColumn = "collection_id"
    )
    override val filmDB: List<FilmDB>?
) : EntityCollectionWithFilms