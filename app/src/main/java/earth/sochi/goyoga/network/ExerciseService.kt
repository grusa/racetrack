package earth.sochi.goyoga.network

import earth.sochi.goyoga.database.Exercise
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ExerciseService {
    @GET("exercise")
    fun getExercises() : Call<List<Exercise>>
//don't use suspend. In RETRIFIT it's not working(?!)

    @GET("exercise/{id}")
    fun getExercise(@Path("id") id: Int): Call<Exercise>


}
