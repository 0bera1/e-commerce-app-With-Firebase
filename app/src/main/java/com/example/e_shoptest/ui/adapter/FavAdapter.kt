package com.example.e_shoptest.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ViewholderFavBinding
import com.example.e_shoptest.ui.activity.DetailActivity
import com.example.e_shoptest.ui.model.ItemsModel

class FavAdapter(
    private val favitems: MutableList<ItemsModel>
) : RecyclerView.Adapter<FavAdapter.Viewholder>() {

    private var context: Context? = null

    class Viewholder(val binding: ViewholderFavBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderFavBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavAdapter.Viewholder, position: Int) {
        val item = favitems[position]
        holder.binding.priceTxtOnFav.text = "$${item.price}"
        holder.binding.titleTxt.text = item.title
        holder.binding.ratingTXTOnFav.text = item.rating.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())

//        val imageUrl = if (item.picUrl.isNotEmpty()) item.picUrl[0] else null

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.picOnFav)

        holder.binding.favimgOnFav.setImageResource(
            if (item.fav) R.drawable.btn_3_1 else R.drawable.btn_3
        )
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", item)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = favitems.size
}
