package earth.sochi.racetrack.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = "workout_table")
data class Workout constructor(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
        val id:Long,
        val name: String,
        val type: Long,
        val title: String,
        val description: String,
        val thumbnail: String,
        val date:Long){
        override fun toString() = "$name $type at $date"
}
@Entity(tableName = "workout_type_table")
data class WorkoutType constructor(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
        val id:Long,
        val name: String){
        override fun toString() = "$id|$name"
}
@Entity(tableName = "my_location_table")
data class MyLocationEntity(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
        val id: Long,
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val foreground: Boolean = true,
        val date: Long = 0
) {
        override fun toString(): String {
                val appState = if (foreground)
                { "in app"}
                else { "in BG" }
                return "$latitude, $longitude $appState on " +
                        "${DateFormat.getDateTimeInstance().format(date)}.\n"
        }
}



