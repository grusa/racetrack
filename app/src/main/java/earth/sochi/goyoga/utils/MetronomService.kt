package earth.sochi.goyoga.utils

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.os.IBinder
import android.util.Log
import earth.sochi.goyoga.R

class MetronomService : Service(), Runnable {
    var handler: Handler? = null
    val TAG = "MS"
    var soundPool: SoundPool? = null
    var soundId = 0
    var timer = 0
    var count = 0
    override fun onCreate() {
        super.onCreate()
        val sp = SoundPool.Builder()
        sp.setMaxStreams(5)
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .build()

        soundId = soundPool!!.load(this, R.raw.beep, 5)
        handler = Handler()
        handler!!.post(this)
        Log.d(TAG,"onCreate $timer")
    }

    override fun onDestroy() {
        handler!!.removeCallbacks(this)
        super.onDestroy()
    }

    //@Nullable
    override fun onBind(intent: Intent): IBinder? {
        timer = intent.getIntExtra("timer", 0)
        Log.d(TAG,"onBind:$timer")
        return null
    }

    override fun run() {
        Log.d(TAG,"run():$timer and $count")
        if (timer > 0) {
            soundPool!!.play(soundId, 1f, 1f, 5, 0, 1.toFloat())
            handler!!.postDelayed(this, timer.toLong())
            count++
        }
    }
}
