package earth.sochi.racetrack.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HiitNetworkDao {
    private val BASE_URL = "sochi.earth"
    private var instance: Retrofit? = null

    fun getInstance(): Retrofit? {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance
    }

    fun getHiitDao(): HiitService? {
        return getInstance()?.create(HiitService::class.java)
    }
}