package earth.sochi.goyoga.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
        @Query("SELECT id, name, type, title, description, thumbnail, date FROM workout_table ORDER BY id")
        fun getAllWorkouts(): Flow<List<Workout>>
        @Query("SELECT id, name, type, title, description, thumbnail, date FROM workout_table WHERE id = :id ORDER BY name")
        fun getWorkoutById(id: Long): Flow<List<Workout>>
        @Query("SELECT id, name, type, title, description, thumbnail, date FROM workout_table WHERE date = :date")
        fun getWorkoutByDate(date: Long): Flow<List<Workout>>
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertAll(workouts: List<Workout>)
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(workout: Workout)
}