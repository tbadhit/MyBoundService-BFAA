package com.tbadhit.myservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.tbadhit.myservice.databinding.ActivityMainBinding

// Membuat Service yang berjalan di background (without stop service):
// Update "activity_main.xml"
// Add code "MainActivity" (1)
// Create new Service "MyService" (klik kanan pada package project → New → Service → Service)
// See manifest and MyService will added in there
// add code "MyService" (1)
// Add code "MainActivity" (2)
// Run APP

// Membuat sebuah background process untuk mematikan/menghentikan service yang telah dijalankan dengan memanggil metode stopSelf() setelah 3 detik:
// add library coroutine "build.gradle module" (1)
// add code "MyService" (2)

// JobIntentService :
// Create new service "MyJobIntentService" (klik kanan pada package utama project → New → Service → Service (Intent Service))
// add permission code "AndroidManifest" (1)
// add code "MyJobIntentService" (1)
// Add code "MainActivity" (3)

// Bound Service :
// Create new Service "MyBoundService" (klik kanan pada package project → New → Service → Service)
// add code "MyBoundService" (1)
// Add code "MainActivity" (4) (5)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // (4)
    private var mServiceBound= false
    private lateinit var mBoundService: MyBoundService
    //----

    // (4)
    // ServiceConnection untuk menghubungkan MainActivity dengan MyBoundService
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServiceBound = false
        }

    }
    //-----

    // (5)
    // onDestroy() = Tujuannya agar ketika aplikasi sudah keluar, service akan berhenti secara otomatis.
    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound) {
            /*
            Pemanggilan unbindService di dalam onDestroy ditujukan untuk mencegah memory leaks
            dari bound services
             */
            unbindService(mServiceConnection)
        }
    }
    //-----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // (1)
        binding.apply {
            // (1)
            btnStartService.setOnClickListener {
                // (2)
                val mStartServiceIntent = Intent(this@MainActivity, MyService::class.java)
                startService(mStartServiceIntent)
                //-----

            }

            // (1)
            btnStartJobIntentService.setOnClickListener {
                // (3)
                val mStartIntentService = Intent(this@MainActivity, MyJobIntentService::class.java)
                mStartIntentService.putExtra(MyJobIntentService.EXTRA_DURATION, 5000L)
                MyJobIntentService.enqueueWork(this@MainActivity, mStartIntentService)
            }

            // (1)
            btnStartBoundService.setOnClickListener {
                // (5)
                // mBoundServiceIntent = sebuah intent eksplisit yang digunakan untuk menjalankan komponen dari dalam sebuah aplikasi
                // bindService = digunakan untuk memulai mengikat kelas MyBoundService ke kelas MainActivity.
                val mBoundServiceIntent = Intent(this@MainActivity, MyBoundService::class.java)
                bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
                //----
            }

            // (1)
            btnStopBoundService.setOnClickListener {
                // (5)
                unbindService(mServiceConnection)
                //----
            }
        }
    }
}