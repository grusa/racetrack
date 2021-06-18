package earth.sochi.goyoga.network

import earth.sochi.goyoga.database.Hiit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface HiitService {
    @GET("hiit")
    fun getHiits() : Call<List<Hiit>>
//don't use suspend. In RETRIFIT it's not working(?!)

    @GET("hiit/{id}")
    fun getHiit(@Path("id") id: Int): Call<Hiit>


}
