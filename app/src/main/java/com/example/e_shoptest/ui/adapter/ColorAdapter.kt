package com.example.e_shoptest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ViewholderColorBinding

class ColorAdapter(
    val items: MutableList<String>,
    private val onItemClick: (Int) -> Unit // Tıklama olayları için lambda fonksiyonu eklendi
) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderColorBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.pic)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            onItemClick(position) // Tıklanan öğenin pozisyonu döndürülüyor
        }
        if (selectedPosition == position) {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.gray_bg_selected)
        } else {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.gray_bg)
        }
    }

    fun setSelectedPosition(position: Int) {
        lastSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(selectedPosition)
    }

    inner class ViewHolder(val binding: ViewholderColorBinding) :
        RecyclerView.ViewHolder(binding.root)
}
