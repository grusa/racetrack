package earth.sochi.racetrack.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WorkoutTypeRepository (private val workoutTypeDao:WorkoutTypeDao) {
        val allWorkoutTypes: Flow<List<WorkoutType>> = workoutTypeDao.getWorkoutTypes()
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(workoutType: WorkoutType) {
            workoutTypeDao.insert(workoutType)
        }
}