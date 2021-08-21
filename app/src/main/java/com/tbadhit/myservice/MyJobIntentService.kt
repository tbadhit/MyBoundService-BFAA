package com.tbadhit.myservice

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.core.app.JobIntentService

// (1) Extends "JobIntentService()"
class MyJobIntentService : JobIntentService() {
    // (1)
    override fun onHandleWork(intent: Intent) {
        // (1)
        Log.d(TAG, "onHandleWork: Mulai....")
        val duration = intent.getLongExtra(EXTRA_DURATION,0)
        try {
            Thread.sleep(duration)
            Log.d(TAG, "onHandleWork: Selesai...")
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }

    // (1)
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    // (1)
    companion object {
        private const val JOB_ID = 1000
        internal const val EXTRA_DURATION = "extra_duration"
        private val TAG = MyJobIntentService::class.java.simpleName

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, intent)
        }
    }
}