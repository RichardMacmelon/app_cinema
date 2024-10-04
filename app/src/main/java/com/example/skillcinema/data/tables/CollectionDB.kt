package com.example.skillcinema.data.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillcinema.entity.entityForDB.EntityCollectionDB

@Entity(tableName = "collection")
data class CollectionDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collection_id")
    override val collectionId: Int = 0,
    @ColumnInfo(name = "collection_name")
    override val collectionName: String,
    @ColumnInfo(name = "collection_size")
    override val collectionSize: Int
) : EntityCollectionDB
