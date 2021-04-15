package earth.sochi.racetrack.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HiitDao {
    @Query("SELECT id, name, duration,description,imageurl,email FROM hiit_table ORDER BY id")
    fun getHiits(): Flow<List<Hiit>>

    @Query("SELECT id, name, duration,description,imageurl,email  FROM hiit_table WHERE id = :id ORDER BY name")
    fun getHiitById(id: Long): Flow<List<Hiit>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(hiits: List<Hiit>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hiit: Hiit)

    @Query("DELETE FROM hiit_table")
    suspend fun deleteAll()
}