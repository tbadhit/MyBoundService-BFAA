package com.tbadhit.myservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

    // (1)
    private var mBinder = MyBinder()
    private val startTime = System.currentTimeMillis()
    //-----

    // (1)
    // MyBinder = Fungsinya untuk mengikat kelas service
    internal inner class MyBinder: Binder() {
        val getService: MyBoundService = this@MyBoundService
    }
    // (1)

    // (1)
    // Variable untuk menghitung mundur
    private var mTimer: CountDownTimer = object : CountDownTimer(100000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val elapsedTime = System.currentTimeMillis() - startTime
            Log.d(TAG, "onTick: $elapsedTime")
        }

        override fun onFinish() {

        }
    }

    // (1)
    // onCreate() dipanggil ketika memulai pembentukan kelas MyBoundService
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    // (1)
    /*
    onBind() service akan berjalan dan diikatkan atau ditempelkan dengan activity pemanggil.
    Pada metode ini juga, mTimer akan mulai berjalan
     */
    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: ")
        mTimer.start()
        return mBinder
    }

    // (1)
    /*
    onDestroy() = berfungsi untuk melakukan penghapusan kelas MyBoundService dari memori.
    Jadi setelah service sudah terlepas dari kelas MainActivity, kelas MyBoundService juga
    terlepas dari memori android
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    // (1)
    // onUnbind() melepaskan service dari activity pemanggil
    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        mTimer.cancel()
        return super.onUnbind(intent)
    }

    // (1)
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }

    // (1)
    companion object {
        private val TAG = MyBoundService::class.java.simpleName
    }
}