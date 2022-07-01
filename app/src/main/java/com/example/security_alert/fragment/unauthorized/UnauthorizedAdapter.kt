package com.example.security_alert.fragment.unauthorized

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.security_alert.databinding.ItemUnauthorizedReportBinding
import com.example.security_alert.fragment.unauthorized.model.UnauthorizedContent


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

class UnauthorizedAdapter (private val clickListener: UnauthorizedAdapterClickListener) : RecyclerView.Adapter<UnauthorizedAdapter.ViewHolder>() {

    private var items: List<UnauthorizedContent> = arrayListOf()

    fun updateData(items: List<UnauthorizedContent>) {
        this.items = emptyList()
        this.items = items
        notifyDataSetChanged()
    }



    class ViewHolder private constructor(private val binding: ItemUnauthorizedReportBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: UnauthorizedAdapterClickListener,item: UnauthorizedContent) {
            binding.clickListener = clickListener
            binding.unauthorized = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUnauthorizedReportBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, items[position])
    }

    override fun getItemCount(): Int = items.size
}

interface UnauthorizedAdapterClickListener{
    fun onItemClick(item: UnauthorizedContent)
}