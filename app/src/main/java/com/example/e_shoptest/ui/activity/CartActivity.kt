package com.example.e_shoptest.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ActivityCartBinding
import com.example.e_shoptest.ui.adapter.CartAdapter
import com.example.e_shoptest.ui.helper.ManagmentCart
import com.example.e_shoptest.ui.helper.ChangeNumberItemsListener
import com.google.android.material.snackbar.Snackbar

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax : Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        setVariable()
        initCartList()
        calculateCart()
        initBuy()
    }

    private fun initBuy() {
        binding.CheckBtn.setOnClickListener {
            Snackbar.make(binding.root, "Thank you for your order", Snackbar.LENGTH_SHORT).show()
            clearCart()
        }
    }
    private fun clearCart() {
        managmentCart.clearCart()
        initCartList()
        calculateCart()
    }

    private fun initCartList() {
        binding.ViewCart.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager
                .VERTICAL,false)
        binding.ViewCart.adapter = CartAdapter(managmentCart.getListCart(), this,object : ChangeNumberItemsListener{
            override fun onChanged() {
                calculateCart()
            }

        })
        with(binding){
            emptyTxt.visibility =
                if (managmentCart.getListCart().isEmpty())
                    View.VISIBLE else View.GONE

            scCart.visibility =
                if (managmentCart.getListCart().isEmpty())
                    View.GONE else View.VISIBLE
        }

    }
    private fun calculateCart() {
        val percentTax = 0.02
        val delivery=10.0
        tax = Math.round((managmentCart.getTotalFee()*percentTax)*100)/100.0
        val total = Math.round((managmentCart.getTotalFee()+delivery+tax)*100)/100.0
        val itemTotal=Math.round(managmentCart.getTotalFee()*100)/100.0

//        with(binding){
            binding.totalFeeTxt.text = "$$itemTotal"
            binding.deliveryTxt.text = "$$delivery"
            binding.totalTaxTxt.text = "$$tax"
            binding. totalTxt.text = "$$total"
//        }
    }
    private fun setVariable() {
        binding.BackBtn.setOnClickListener { finish() }
    }

}