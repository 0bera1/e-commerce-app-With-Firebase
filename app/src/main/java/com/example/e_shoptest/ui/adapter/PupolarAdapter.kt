package com.example.e_shoptest.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ViewholderRecommendedBinding
import com.example.e_shoptest.ui.activity.DetailActivity
import com.example.e_shoptest.ui.model.ItemsModel
import com.example.e_shoptest.ui.viewmodel.MainViewModel

class PupolarAdapter (
//    private val favitems: MutableList<ItemsModel> ,
    val items : MutableList<ItemsModel>) :

    RecyclerView.Adapter<PupolarAdapter.ViewHolder>() {

        private var context: Context? = null
    private lateinit var viewModel: MainViewModel

   inner class ViewHolder (val binding: ViewholderRecommendedBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PupolarAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PupolarAdapter.ViewHolder, position: Int) {
//        val item = favitems[position]


        holder.binding.titleTxt.text = items[position].title
        holder.binding.priceTxt.text = "$" + items[position].price.toString()
        holder.binding.ratingTXT.text = items[position].rating.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.pic1)

        holder.binding.favimgOnFav.setImageResource(
            if (items[position].fav) R.drawable.btn_3_1 else R.drawable.btn_3
        )
        holder.binding.favimgOnFav.setOnClickListener {

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}