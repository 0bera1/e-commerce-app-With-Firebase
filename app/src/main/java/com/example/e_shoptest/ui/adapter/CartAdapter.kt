package com.example.e_shoptest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.example.e_shoptest.databinding.ActivityCartBinding
import com.example.e_shoptest.databinding.ViewholderCartBinding
import com.example.e_shoptest.databinding.ViewholderSizeBinding
import com.example.e_shoptest.ui.helper.ManagmentCart
import com.example.e_shoptest.ui.model.ItemsModel
import com.example.e_shoptest.ui.helper.ChangeNumberItemsListener
import com.google.ai.client.generativeai.common.RequestOptions

class CartAdapter (private val listItemSelected : ArrayList<ItemsModel>,
                   context: Context,
                   var changeNumberItemsListener : ChangeNumberItemsListener?=null) : RecyclerView.Adapter<CartAdapter.Viewholder>() {
    class Viewholder  (val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    private val managmentCart = ManagmentCart(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.Viewholder {
        val binding= ViewholderCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.Viewholder, position: Int) {
        val item = listItemSelected[position]

        holder.binding.titleTxt.text=item.title
        holder.binding.feeEachItem.text="$${item.price}"
        holder.binding.totalEachItem.text="$${Math.round(item.numberInCart*item.price)}"
        holder.binding.numberItemTxt.text=item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(com.bumptech.glide.request.RequestOptions().transform(CenterCrop()))
            .into(holder.binding.pic)

        holder.binding.plusCartBtn.setOnClickListener {
            managmentCart.plusItem(
                listItemSelected,position,object : ChangeNumberItemsListener{
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
        holder.binding.minusCartBtn.setOnClickListener {
            managmentCart.minusItem(
                listItemSelected,position,object : ChangeNumberItemsListener{
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = listItemSelected.size
}