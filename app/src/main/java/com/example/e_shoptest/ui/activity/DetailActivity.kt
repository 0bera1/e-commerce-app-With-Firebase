package com.example.e_shoptest.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ActivityDashboardBinding
import com.example.e_shoptest.databinding.ActivityDetailBinding
import com.example.e_shoptest.ui.adapter.ColorAdapter
import com.example.e_shoptest.ui.adapter.SizeAdapter
import com.example.e_shoptest.ui.adapter.SliderAdapter
import com.example.e_shoptest.ui.helper.ManagmentCart
import com.example.e_shoptest.ui.model.ItemsModel
import com.example.e_shoptest.ui.model.SliderModel
import com.example.e_shoptest.ui.viewmodel.MainViewModel

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managementCart: ManagmentCart
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // ViewModel başlatılması
        managementCart = ManagmentCart(this)

        getBundle()
        banners()
        initLists()
        setupListeners() // Slider ve colorList için dinleyiciler ekleniyor
        addFavC()

        binding.favBtn.setImageResource(
            if (item.fav) R.drawable.btn_3_1 else R.drawable.btn_3
        )
    }

    private fun addFavC(){
        binding.favBtn.setOnClickListener {
            if ( !item.fav) {
                item.fav = true
                binding.favBtn.setImageResource(R.drawable.btn_3_1)
            } else {
                item.fav = false
                binding.favBtn.setImageResource(R.drawable.btn_3)
            }
        }
    }
    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in item.size) {
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }
        val colorAdapter = ColorAdapter(colorList) { position ->
            binding.slider.setCurrentItem(position, true) // colorList'te tıklanan öğe slider'a güncelleniyor
        }
        binding.colorList.adapter = colorAdapter
        binding.colorList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val slidersItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            slidersItems.add(SliderModel(imageUrl))
        }

        binding.slider.adapter = SliderAdapter(slidersItems, binding.slider)
        binding.slider.clipToPadding = true
        binding.slider.clipChildren = true
        binding.slider.offscreenPageLimit = 3
        binding.slider.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        if (slidersItems.size > 1) {
            binding.dotsIndicator2.visibility = View.VISIBLE
            binding.dotsIndicator2.attachTo(binding.slider)
        }
    }

    private fun setupListeners() {
        binding.slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                (binding.colorList.adapter as ColorAdapter).setSelectedPosition(position) // Slider'da sayfa değiştirildiğinde colorList güncelleniyor
            }
        })
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.priceTxt.text = "$" + item.price
        binding.DescriptionTxt.text = item.description
        binding.ratingTXT.text = "${item.rating} Rating"
        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managementCart.insertFood(item)
        }
        binding.BackBtn.setOnClickListener { finish() }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }
}
