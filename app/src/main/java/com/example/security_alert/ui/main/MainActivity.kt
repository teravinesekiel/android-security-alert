package com.example.security_alert.ui.main

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.security_alert.R
import com.example.security_alert.ui.unauthorized.UnauthorizedActivity

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
        val rvAlert = findViewById<RecyclerView>(R.id.rvAlertUser)
        val alertUsers = ArrayList<AlertUserModel>()
        alertUsers.add(AlertUserModel())
        rvAlert.adapter = AlertUserAdapter(alertUsers)
    }

    private fun showUnAuthorizedAlert(floor:String, base64:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("New Unauthorized Data Arrived")

        builder.setNegativeButton("Show!!!"
        ) { p0, p1 -> startActivity(Intent(this@MainActivity, UnauthorizedActivity::class.java).apply {
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