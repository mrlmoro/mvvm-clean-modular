package br.com.murilomoro.data.local.db

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import br.com.murilomoro.data.R
import br.com.murilomoro.data.local.db.movie.FavoriteMovieEntity
import br.com.murilomoro.data.local.db.movie.MovieDao

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(application: Application): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database_name)
            ).allowMainThreadQueries().build()
        }
    }

    abstract fun movieDao(): MovieDao
}