package com.example.demo.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.preference.PreferenceFragmentCompat
import com.example.demo.R

class LifecycleTest : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.d("joy", "LifecycleTest onCreate")
    }
}
class SettingsActivity : AppCompatActivity() {
    var lifecycleTest = LifecycleTest()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.d("joy", "SettingsActivity onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.d("joy", "SettingsActivity onResume")
    }

    override fun onStart() {
        super.onStart()
        lifecycle.addObserver(lifecycleTest)
        Log.d("joy", "SettingsActivity onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("joy", "SettingsActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("joy", "SettingsActivity onDestroy")
    }
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}