package com.example.security_alert.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.security_alert.R

class AlertUserAdapter constructor(private val alertUsers: List<AlertUserModel>) :
    RecyclerView.Adapter<AlertUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv_alert_user, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageBytes = Base64.decode(alertUsers[position].photo, Base64.NO_WRAP)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.ivPhoto.setImageBitmap(bitmap)
        holder.tvName.text = alertUsers[position].name
        holder.tvTimeAppeared.text = alertUsers[position].timeAppeared
        holder.tvTimeDissapeared.text = alertUsers[position].timeDissappeared
        holder.tvLocation.text = alertUsers[position].location
    }

    override fun getItemCount(): Int = alertUsers.size


    inner class ViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        val ivPhoto: ImageView by lazy { item.findViewById(R.id.ivPhoto) }
        val tvName: TextView by lazy { item.findViewById(R.id.tvName) }
        val tvTimeAppeared: TextView by lazy { item.findViewById(R.id.tvTimeAppeared) }
        val tvTimeDissapeared: TextView by lazy { item.findViewById(R.id.tvTimeAppeared) }
        val tvLocation: TextView by lazy { item.findViewById(R.id.tvLocation) }
    }
}