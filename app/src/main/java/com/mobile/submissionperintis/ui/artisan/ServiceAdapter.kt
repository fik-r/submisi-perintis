package com.mobile.submissionperintis.ui.artisan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.submissionperintis.data.model.Service
import com.mobile.submissionperintis.databinding.ItemServiceBinding
import com.mobile.submissionperintis.extension.loadImageFromUrl

class ServiceAdapter() :
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root)


    var serviceList = listOf<Service>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = serviceList[position]
        holder.binding.apply {
            textName.text = service.name
            textPrice.text = "Price: " + service.price
            textCaption.text = service.caption
        }
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}