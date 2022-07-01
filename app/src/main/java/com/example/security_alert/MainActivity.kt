package com.example.security_alert

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    companion object{
        const val PERMISSION_REQUEST_CODE = 1

    }
    private val permissions = arrayOf(
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        if (!hasPermissions(permissions)){
            ActivityCompat.requestPermissions(this, permissions,
                PERMISSION_REQUEST_CODE
            )
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