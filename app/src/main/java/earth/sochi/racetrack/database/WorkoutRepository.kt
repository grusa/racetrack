package earth.sochi.racetrack.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao:WorkoutDao) {
    val allWorkouts: Flow<List<Workout>> = workoutDao.getAllWorkouts()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
}