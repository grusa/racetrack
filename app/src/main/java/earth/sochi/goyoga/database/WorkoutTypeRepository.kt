package earth.sochi.goyoga.database

import android.util.Log
import androidx.annotation.WorkerThread
import com.google.gson.GsonBuilder
import earth.sochi.goyoga.network.ExerciseService
import earth.sochi.goyoga.network.HiitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WorkoutTypeRepository (workoutTypeDao:WorkoutTypeDao,
                             weightDao:WeightDao,
                             workoutDao: WorkoutDao,
                             hiitDao: HiitDao,
                             exerciseDao: ExerciseDao) {
        val TAG:String = "WorkoutTypeRepository"
        val BaseUrl = "http://www.sochi.earth/"
        var retrofit :Retrofit?=null

        private val workoutTypeDao:WorkoutTypeDao
        private val weightDao:WeightDao
        private val workoutDao: WorkoutDao
        private val hiitDao: HiitDao
        private val exerciseDao: ExerciseDao
        init {
                Log.d(TAG, "CONSTRUCTOR")
                this.workoutTypeDao=workoutTypeDao
                this.weightDao = weightDao
                this.workoutDao = workoutDao
                this.hiitDao=hiitDao
                this.exerciseDao = exerciseDao
        }



        val allWorkoutTypes: Flow<List<WorkoutType>> =
                workoutTypeDao.getWorkoutTypes()

        val allHiits : Flow<List<Hiit>>  = hiitDao.getHiits()

        fun allExercisesByHiitId(id :Long) :Flow<List<Exercise>> =
        exerciseDao.getExercisesByHiitId(id)

        fun allExercises() :Flow<List<Exercise>> =
                exerciseDao.getExercises()

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insertExercise(exercise: Exercise)  =   exerciseDao.insert(exercise)
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insertExercises(exercises: List<Exercise>)  =   exerciseDao.insertAll(exercises)

        fun loadHiitsFromSite() {
                val okHttpClient = OkHttpClient().newBuilder()
                        .build()

                val gsonBuilder = GsonBuilder()

                val customGson = gsonBuilder.create()
                retrofit  = Retrofit.Builder()
                        .baseUrl(BaseUrl).client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(customGson))
                        .build()
                if (retrofit!=null) {
                        val hiitService = retrofit!!.create(HiitService::class.java)
                        try {
                                val call = hiitService.getHiits()
                                call.enqueue(object : Callback<List<Hiit>> {
                                override fun onResponse(
                                        call: Call<List<Hiit>>,
                                        response: Response<List<Hiit>>
                                ) {
                                        if (response.code() == 200) {
                                                try {
                                                        runBlocking {
                                                                hiitDao.deleteAll()
                                                                hiitDao.insertAll(response.body()!!)
                                                        }
                                                }catch (e:Exception) {
                                                        Log.d(TAG,e.toString())
                                                }
                                        }
                                }
                                override fun onFailure(call: Call<List<Hiit>>, t: Throwable) {
                                        Log.d(TAG, t.message.toString())
                                }
                        })
                        } catch (e:Exception) {
                                Log.d(TAG,e.toString())
                        }
                }


        }
        fun loadExercisesFromSite() {
                val okHttpClient = OkHttpClient().newBuilder()
                        .build()
                val gsonBuilder = GsonBuilder()
                val customGson = gsonBuilder.create()
                retrofit  = Retrofit.Builder()
                        .baseUrl(BaseUrl).client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(customGson))
                        .build()
                if (retrofit!=null) {
                        val exerciseService = retrofit!!.create(ExerciseService::class.java)
                        try {
                                val call = exerciseService.getExercises()
                                call.enqueue(object : Callback<List<Exercise>> {
                                        override fun onResponse(
                                                call: Call<List<Exercise>>,
                                                response: Response<List<Exercise>>
                                        ) {
                                                if (response.code() == 200) {
                                                        try {
                                                                runBlocking {
                                                                        exerciseDao.deleteAll()
                                                                        exerciseDao.insertAll(response.body()!!)
                                                                }
                                                        }catch (e:Exception) {
                                                                Log.d(TAG,e.toString())
                                                        }
                                                }
                                        }
                                        override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                                                Log.d(TAG, t.message.toString())
                                        }
                                })
                        } catch (e:Exception) {
                                Log.d(TAG,e.toString())
                        }
                }
        }

        val allWeights:Flow<List<Weight>> = weightDao.getWeights()
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(workoutType: WorkoutType) {
            workoutTypeDao.insert(workoutType)
        }
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun deleteWeightById(id :Long) {
                weightDao.deleteWeightById(id)
        }

        suspend fun lastWeight() :Weight = weightDao.lastWeight()

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insertWeight(weight: Weight) {
                weightDao.insert(weight)
        }

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insertHiit(hiit: Hiit) {
                hiitDao.insert(hiit)
        }

        val allWorkouts: Flow<List<Workout>> = workoutDao.getAllWorkouts()

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insertWorkout(workout: Workout) {
                workoutDao.insert(workout)
        }
}