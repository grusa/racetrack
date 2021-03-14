package earth.sochi.racetrack.utils

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.os.IBinder
import android.util.Log
import earth.sochi.racetrack.R

class BreathService : Service(), Runnable {
    var handler: Handler? = null
    val TAG = "MS"
    var soundPool: SoundPool? = null
    var soundId = 0
    var inhale = 0
    var firstPause = 0
    var exhale = 0
    var secondPause = 0
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
    }

    override fun onDestroy() {
        handler!!.removeCallbacks(this)
        super.onDestroy()
    }

    //@Nullable
    override fun onBind(intent: Intent): IBinder? {
        inhale = intent.getIntExtra("inhale", 0)
        firstPause = intent.getIntExtra("firstPause", 0)
        exhale = intent.getIntExtra("exhale", 0)
        secondPause = intent.getIntExtra("secondPause", 0)
        return null
    }

    override fun run() {
        if (inhale>0 && exhale>0 ) {
            soundPool!!.play(soundId, 1f, 1f, 5, 0, 1.toFloat())
            handler!!.postDelayed(this, inhale.toLong())
            count++
        }
    }
}
