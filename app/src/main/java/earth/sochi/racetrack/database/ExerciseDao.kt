package earth.sochi.racetrack.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT id,hiit_id, name, duration,description,imageurl FROM exercise_table ORDER BY id")
    fun getExercises(): Flow<List<Exercise>>

    @Query("SELECT id,hiit_id, name, duration,description,imageurl  FROM exercise_table WHERE id = :id ORDER BY name")
    fun getExerciseById(id: Long): Flow<List<Exercise>>

    @Query("SELECT id,hiit_id, name, duration,description,imageurl  FROM exercise_table WHERE hiit_id = :id ORDER BY name")
    fun getExercisesByHiitId(id: Long): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(exercises: List<Exercise>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exercise: Exercise)

    @Query("DELETE FROM exercise_table")
    suspend fun deleteAll()
}