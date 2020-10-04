package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val BROADCAST_STRING_AIROPLANE_MODE = "android.intent.action.AIRPLANE_MODE"
    private val BROADCAST_STRING_TIMEZONE = "android.intent.action.TIMEZONE_CHANGED"
    private val BROADCAST_STRING_TIME_SET = "android.intent.action.TIME_SET"
    private val ACTION_TIMEZONE_TEXT = "Была изменена временная зона"
    private val ACTION_AIROPLANE_TEXT = "Была нажата кнопка \"Режим полёта\""
    private val ACTION_TIME_TEXT = "Было изменено время"
    private val KEY = "myKey"
    private lateinit var mIntent: Intent
    private var choiceStorage = 0
    private var broadcastReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        buttonStart.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainActivity, BlankFragment())
                    .commit()
        })
        setBroadcast()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.internalStorage -> {
                Toast.makeText(this, "События будут сохранены во внутреннем хранилище", Toast.LENGTH_SHORT).show()
                choiceStorage = 1
            }
            R.id.externalStorage -> {
                Toast.makeText(this, "События будут сохранены во внешнем хранилище", Toast.LENGTH_SHORT).show()
                choiceStorage = 2
            }
        }
        savePreferencesStorage(choiceStorage, KEY)
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun savePreferencesStorage(i: Int, key: String) {
        val preferencesSave = getSharedPreferences(key, MODE_PRIVATE)
        val editor = preferencesSave.edit()
        editor.putInt("storage", i)
        editor.apply()
    }

    private fun loadPreferencesStorage(key: String): Int {
        val preferencesLoad = getSharedPreferences(key, MODE_PRIVATE)
        return preferencesLoad.getInt("storage", -1)
    }

    private fun setBroadcast() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context, i: Intent) {
                if (Intent.ACTION_TIMEZONE_CHANGED == i.action) {
                    mIntent.putExtra("infoString", ACTION_TIMEZONE_TEXT)
                } else if (Intent.ACTION_AIRPLANE_MODE_CHANGED == i.action) {
                    mIntent.putExtra("infoString", ACTION_AIROPLANE_TEXT)
                } else if (Intent.ACTION_TIME_CHANGED == i.action) {
                    mIntent.putExtra("infoString", ACTION_TIME_TEXT)
                }
                if (choiceStorage == 0) {
                    mIntent.putExtra("infoInt", loadPreferencesStorage(KEY))
                } else {
                    mIntent.putExtra("infoInt", choiceStorage)
                }
                startService(mIntent)
            }
        }
    }

    private fun registerReceiver() {
        val intentFilterAirplaneMode = IntentFilter(BROADCAST_STRING_AIROPLANE_MODE)
        val intentFilterTimeZone = IntentFilter(BROADCAST_STRING_TIMEZONE)
        val intentFilterTimeSet = IntentFilter(BROADCAST_STRING_TIME_SET)
        registerReceiver(broadcastReceiver, intentFilterAirplaneMode)
        registerReceiver(broadcastReceiver, intentFilterTimeZone)
        registerReceiver(broadcastReceiver, intentFilterTimeSet)
        mIntent = Intent(this, ServiceActivity::class.java)
    }
}