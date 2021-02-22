package earth.sochi.racetrack.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

private const val DATABASE_NAME = "workout-database"

@Database(entities = [Workout::class,WorkoutType::class,MyLocationEntity::class], version = 1)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract val workoutDao: WorkoutDao
    abstract val workoutTypeDao: WorkoutTypeDao
    abstract val myLocationDao: MyLocationDao

//    abstract fun locationDao(): MyLocationDao

/*    private lateinit var INSTANCE: WorkoutDatabase

    fun getDatabase(context: Context): WorkoutDatabase {
        synchronized(WorkoutDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workouts_database"
                ).build()
            }
        }
        return INSTANCE
    }*/

    companion object {
        // For Singleton instantiation
        @Volatile private var INSTANCE: WorkoutDatabase? = null

        fun getInstance(context: Context): WorkoutDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): WorkoutDatabase {
            return Room.databaseBuilder(
                context,
                WorkoutDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}