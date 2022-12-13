package com.mobileapp.deafassist.data

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Entity(tableName = "favorites_table")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "text") val text: String,
)

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoriteDao

    private class FavoriteDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val favoritesDao = database.favoritesDao()

                    // Delete all content here.
                    favoritesDao.deleteAll()

                    // Add sample favorites.
                    favoritesDao.insert(FavoriteEntity("Hi! Nice to meet you."))
                    favoritesDao.insert(FavoriteEntity("How are you doing today?"))
                    favoritesDao.insert(FavoriteEntity("It was great speaking with you."))
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FavoriteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorites_database"
                ).addCallback(FavoriteDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites_table")
    fun getAll(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)

    @Update
    fun update(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAll()
}

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FavoriteRepository(private val favoritesDao: FavoriteDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allFavorites: Flow<List<FavoriteEntity>> = favoritesDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(favorite: FavoriteEntity) {
        favoritesDao.insert(favorite)
    }
}


