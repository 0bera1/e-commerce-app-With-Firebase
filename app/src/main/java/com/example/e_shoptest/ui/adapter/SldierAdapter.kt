package com.example.e_shoptest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.e_shoptest.databinding.SliderItemContainerBinding
import com.example.e_shoptest.ui.model.SliderModel

class SliderAdapter(
    private val items: List<SliderModel>,
    private val viewPager: ViewPager2 // ViewPager2 referansı eklendi
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = SliderItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.itemView.context)
            .load(item.url)
            .into(holder.binding.imageslide)

        holder.binding.imageslide.setOnClickListener {
            viewPager.setCurrentItem(position, true) // Tıklanan öğeye geçiş yapılır
        }
    }

    override fun getItemCount(): Int = items.size

    inner class SliderViewHolder(val binding: SliderItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root)
}
