package earth.sochi.racetrack.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val DATABASE_NAME = "workout-database"

@Database(entities = [Workout::class,WorkoutType::class,MyLocationEntity::class], version = 1)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutTypeDao(): WorkoutTypeDao
    abstract fun myLocationDao(): MyLocationDao
    private class WorkoutDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //Create database
                    val workoutTypeDao = database.workoutTypeDao()
                    workoutTypeDao.deleteAll()
                    var wt = WorkoutType (0,"StopWatch")
                    workoutTypeDao.insert(wt)
                    //wt =WorkoutType (1,"Timer")
                    //workoutTypeDao.insert(wt)
                    wt =WorkoutType (2,"Timer")
                    workoutTypeDao.insert(wt)
                    wt =WorkoutType (3,"Metronome")
                    workoutTypeDao.insert(wt)
                    wt =WorkoutType (4,"Breath")
                    workoutTypeDao.insert(wt)
//                    wt =WorkoutType (5,"Pranayama")
//                    workoutTypeDao.insert(wt)
                    wt =WorkoutType (6,"Run")
                    workoutTypeDao.insert(wt)
//                    wt =WorkoutType (7,"Walking")
//                    workoutTypeDao.insert(wt)
                    wt =WorkoutType (8,"HIIT")
                    workoutTypeDao.insert(wt)

                }
            }
        }

    }
    companion object {
        // For Singleton instantiation
        @Volatile
        private var INSTANCE: WorkoutDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): WorkoutDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workout_database"
                )
                    .addCallback(WorkoutDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
