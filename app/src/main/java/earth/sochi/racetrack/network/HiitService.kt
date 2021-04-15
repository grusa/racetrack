package earth.sochi.racetrack.network

import com.google.gson.JsonObject
import earth.sochi.racetrack.database.Hiit
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface HiitService {
    @GET("hiit")
    fun getHiits() : Call<List<Hiit>>
//don't use suspend. In RETRIFIT it's not working(?!)

    @GET("hiit/{id}")
    fun getHiit(@Path("id") id: Int): Call<Hiit>


}
