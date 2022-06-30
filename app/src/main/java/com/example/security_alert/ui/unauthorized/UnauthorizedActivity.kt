package com.example.security_alert.ui.unauthorized

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import com.example.security_alert.R

class UnauthorizedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unauthorized)
        init()
    }

    private fun init() {
        val floor = intent.getStringExtra("floor")
        val base64 = intent.getStringExtra("base64")

        val tvFloor = findViewById<TextView>(R.id.tvFloor)
        val ivFace = findViewById<ImageView>(R.id.ivFace)

        tvFloor.setText("Floor : $floor")
        val decodedBase64 = Base64.decode(base64, Base64.NO_WRAP)
        val bitmap = BitmapFactory.decodeByteArray(decodedBase64, 0, decodedBase64.size)
        ivFace.setImageBitmap(bitmap)

    }


}