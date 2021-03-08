package earth.sochi.racetrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.racetrack.viewmodels.TimeManagerViewModel
import earth.sochi.racetrack.viewmodels.WorkoutTypeViewModel
import earth.sochi.racetrack.workout.WorkoutTypeListAdapter
import earth.sochi.racetrack.workout.WorkoutFragment


class MainActivity : AppCompatActivity() {
    val TAG="MA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
        setContentView(R.layout.activity_main)
        } catch(e:Exception) {
            Log.d(TAG,e.toString())
            throw e
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_experimental -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}