package earth.sochi.racetrack.database

import android.location.Location
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
@Entity(tableName = "hiit_table")
data class Hiit constructor(
        @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id")
        val id:Long,
        val name: String,
        val duration: Long, //minutes
        val description: String,
        val imageurl: String,
        val email:String ){
        override fun toString() = "HIIT:$name - $duration minutes by $email"
}
@Entity(tableName = "exercise_table")
data class Exercise constructor(
        @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id")
        val id:Long,
        val hiit_id:Long,
        val name: String,
        val duration: Long, //minutes
        val description: String,
        val imageurl: String ){
        override fun toString() = "EXERCISE:$name - $duration minutes"
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
@Entity(tableName = "weight_table")
data class Weight(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
        val id: Long,
        var weight_kilo: Int = 0,
        val weight_gramm: Int = 0,
        val date: Long= 0
)

data class LocationModel(
        val location : Location,
        val longitude: Double,
        val latitude: Double
)



