package com.mobile.submissionperintis.ui.artisan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.submissionperintis.data.model.Artisan
import com.mobile.submissionperintis.databinding.ItemArtisanBinding
import com.mobile.submissionperintis.extension.loadImageFromUrl

class ListAdapter(
    private val onItemClick: ((id: String) -> Unit)? = null,
) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemArtisanBinding) : RecyclerView.ViewHolder(binding.root)


    var artisanList = listOf<Artisan>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArtisanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artisan = artisanList[position]
        holder.binding.apply {
            textName.text = artisan.name
            textDescription.text = artisan.description
            img.loadImageFromUrl(artisan.image)

            root.setOnClickListener {
                onItemClick?.invoke(artisan.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return artisanList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}