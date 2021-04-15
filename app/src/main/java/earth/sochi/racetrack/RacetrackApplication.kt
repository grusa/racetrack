package earth.sochi.racetrack

import android.app.Application
import earth.sochi.racetrack.database.WorkoutDatabase
import earth.sochi.racetrack.database.WorkoutTypeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RacetrackApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WorkoutDatabase.getDatabase(this,applicationScope) }
    val workoutTypeRepository by lazy { WorkoutTypeRepository(database.workoutTypeDao(),
        database.weightDao(),database.workoutDao(),
        database.hiitDao(),
        database.exerciseDao()
    ) }
    //val workoutRepository by lazy { WorkoutRepository(database.workoutDao(),database.weightDao()) }
}