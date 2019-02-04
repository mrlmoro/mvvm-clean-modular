package br.com.murilomoro.data.local.db.movie

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Long
)