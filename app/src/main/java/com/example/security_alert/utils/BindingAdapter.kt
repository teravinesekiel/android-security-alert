package com.example.security_alert.utils

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.security_alert.fragment.unauthorized.UnauthorizedAdapter
import com.example.security_alert.fragment.unauthorized.model.UnauthorizedContent
import com.squareup.picasso.Picasso


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */


@BindingAdapter("app:unauthorizedItems")
fun setUnauthorizedItems(recyclerView: RecyclerView, items: List<UnauthorizedContent>? ) {
    if (items != null ) {
        (recyclerView.adapter as UnauthorizedAdapter).updateData(items)
    }
}


@BindingAdapter("app:base64Image")
fun setBase64Image(imageView: ImageView, base64: String) {
    val imageBytes = Base64.decode(base64, Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    imageView.setImageBitmap(decodedImage)

}