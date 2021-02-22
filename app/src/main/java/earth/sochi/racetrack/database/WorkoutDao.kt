package earth.sochi.racetrack.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkoutDao {
        @Query("SELECT id, name, type, title, description, thumbnail, date FROM workout_table ORDER BY id")
        fun getAllWorkouts(): LiveData<Workout>
        @Query("SELECT id, name, type, title, description, thumbnail, date FROM workout_table WHERE id = :id ORDER BY name")
        fun getWorkoutById(id: Long): LiveData<List<Workout>>
        @Query("SELECT id, name, type, title, description, thumbnail, date FROM workout_table WHERE date = :date")
        fun getWorkoutByDate(date: Long): LiveData<Workout>
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertAll(workouts: List<Workout>)
}