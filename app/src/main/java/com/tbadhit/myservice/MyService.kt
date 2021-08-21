package com.tbadhit.myservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.lang.UnsupportedOperationException

class MyService : Service() {

    // (2)
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    //-----

    // (1)
    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet impelement")
    }

    // (1)
    /*
    Pada metode dibwh kita menjalankan sebuah background process untuk melakukan simulasi proses
    yang sulit. Dan ia berjalan secara asynchronous
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service dijalankan....")
        // (2)
        serviceScope.launch {
            delay(3000)
            // stopSelf() berfungsi untuk memberhentikan atau mematikan MyService dari sistem Android.
            stopSelf()
            Log.d(TAG, "Service dihentikan")
        }
        //-----
        /*
        START_STICKY menandakan bahwa bila service tersebut dimatikan oleh sistem Android karena
        kekurangan memori, ia akan diciptakan kembali jika sudah ada memori yang bisa digunakan
         */
        return START_STICKY
    }

    // (2)
    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, "onDestroy: ")
    }

    // (1)
    companion object {
        internal val TAG = MyService::class.java.simpleName
    }
}