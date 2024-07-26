package com.example.e_shoptest.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ViewholderBrandBinding
import com.example.e_shoptest.databinding.ViewholderColorBinding
import com.example.e_shoptest.databinding.ViewholderSizeBinding
import com.example.e_shoptest.ui.model.BrandModel
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class SizeAdapter (
    val items : MutableList<String>) : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {
        private var selectedPosition = -1
        private var lastSelectedPosition = -1
        private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate( LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.sizeTxt.text=items[position]

        holder.binding.root.setOnClickListener {
            lastSelectedPosition= selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if (selectedPosition == position){
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.gray_bg_selected)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.bg2))
        }else {
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.gray_bg)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }
    inner class ViewHolder (val binding: ViewholderSizeBinding) :
        RecyclerView.ViewHolder(binding.root){

    }
}