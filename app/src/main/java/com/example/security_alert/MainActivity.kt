package com.example.security_alert

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.security_alert.service.MqttService

class MainActivity : AppCompatActivity() {
    companion object{
        const val PERMISSION_REQUEST_CODE = 1

    }
    private val permissions = arrayOf(
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE
    )

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (!hasPermissions(permissions)){
            ActivityCompat.requestPermissions(this, permissions,
                PERMISSION_REQUEST_CODE
            )
        } else {
            init()
        }
    }

    private fun init(){
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val tvFloor = findViewById<TextView>(R.id.tvFloor)
        val ivFace = findViewById<ImageView>(R.id.ivFace)

        tvStatus.text = "Starting MQTT"

        var floor:String? = null
        var base64:String? = null
        var uFloor:String? = null
        var uBase64:String? = null

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.hasExtra("status")) {
                    val status = intent.getStringExtra("status")
                    tvStatus.text = status
                }
                if (intent.hasExtra("floor")) {
                    floor = intent.getStringExtra("floor")
                }

                if (intent.hasExtra("base64")) {
                    base64 = intent.getStringExtra("base64")
                }

                if (intent.hasExtra("floor/unauthorized")){
                    uFloor = intent.getStringExtra("floor/unauthorized")
                }

                if (intent.hasExtra("base64/unauthorized")){
                    uBase64 = intent.getStringExtra("base64/unauthorized")
                }

                if (floor != null && base64 != null){
                    tvFloor.setText("Floor : $floor")

                    val decodedBase64 = Base64.decode(base64, Base64.NO_WRAP)
                    val bitmap = BitmapFactory.decodeByteArray(decodedBase64, 0, decodedBase64.size)
                    ivFace.setImageBitmap(bitmap)
                    val mediaPlayer = MediaPlayer.create(this@MainActivity,R.raw.message_arrived)
                    mediaPlayer.start()

                    mediaPlayer.setOnCompletionListener {
                        floor = null
                        base64 = null
                    }
                }

                if (uFloor != null && uBase64 != null){
                    showUnAuthorizedAlert(uFloor!!,uBase64!!)
                    val mediaPlayer = MediaPlayer.create(this@MainActivity,R.raw.message_alert)
                    mediaPlayer.start()

                    uFloor = null
                    uBase64 = null
                }
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(MqttService.INTENT_FILTER))

        startService(Intent(this, MqttService::class.java))
    }

    private fun showUnAuthorizedAlert(floor:String,base64:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("New Unauthorized Data Arrived")

        builder.setNegativeButton("Show!!!"
        ) { p0, p1 -> startActivity(Intent(this@MainActivity,UnauthorizedActivity::class.java).apply {
            putExtra("floor",floor)
            putExtra("base64",base64)
        }) }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                init()
            }
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        for (permission in permissions){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
}