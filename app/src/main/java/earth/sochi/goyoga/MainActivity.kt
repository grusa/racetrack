package earth.sochi.goyoga

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {
    val TAG="MA"
    var serviceBound:Boolean=true
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