package com.example.broadcast

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ServiceActivity : IntentService("ServiceActivity") {
    override fun onHandleIntent(intent: Intent?) {
        val storage = intent?.getIntExtra("infoInt", 0)
        val label = intent?.getStringExtra("infoString")
        if (storage == 1) {                // internal
            saveDataToStorage(label, storage)
        } else if (storage == 2) {         // external
            saveDataToStorage(label, storage)
        }
    }

    private fun saveDataToStorage(string: String?, integer: Int) {
        var filesDir = filesDir
        if (integer == 2) {
            filesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        }
        val file = File(filesDir, "text.txt")
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = Date()
        try {
            val fileWriter = FileWriter(file, true)
            fileWriter.write(" ${dateFormat.format(date)} - $string \n")
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}