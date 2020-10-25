package edu.uoc.pac2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A book Model representing a piece of content.
 */

@Entity
data class Book
(
        @PrimaryKey val uid: Int? = null,
        @ColumnInfo(name = "titleR") val title: String? = null,
        @ColumnInfo(name = "authorR") val author: String? = null,
        @ColumnInfo(name = "descriptionR") val description: String? = null,
        @ColumnInfo(name = "publicationDateR") val publicationDate: String? = null,
        @ColumnInfo(name = "urlImageR") val urlImage: String? = null
)