package com.example.e_shoptest.ui.activity

import BrandAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ActivityDashboardBinding
import com.example.e_shoptest.ui.adapter.PupolarAdapter
import com.example.e_shoptest.ui.adapter.SliderAdapter
import com.example.e_shoptest.ui.model.BrandModel
import com.example.e_shoptest.ui.model.ItemsModel
import com.example.e_shoptest.ui.model.SliderModel
import com.example.e_shoptest.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.util.Timer
import java.util.TimerTask
import kotlin.system.exitProcess
class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: MainViewModel // ViewModel'ı lateinit var olarak tanımladık
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this).get(MainViewModel::class.java) // ViewModel'ı burada başlattık
        auth = Firebase.auth
        val authRef = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // ViewModel başlatılması


        usersEmailTxt()
        initBanner()
        initBrand()
        initPupolar()
        initBottomMenu()
        logOut(binding.root)
    }


    override fun onBackPressed() {}

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanner()
    }

    private fun banners(images: List<SliderModel>) {
        binding.ViewPagerSlider.adapter = SliderAdapter(images, binding.ViewPagerSlider)
        binding.ViewPagerSlider.clipToPadding = false
        binding.ViewPagerSlider.clipChildren = false
        binding.ViewPagerSlider.offscreenPageLimit = 3
        binding.ViewPagerSlider.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.ViewPagerSlider.setPageTransformer(compositePageTransformer)
        if (images.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE
            binding.dotsIndicator.attachTo(binding.ViewPagerSlider)
        }
    }


    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(this, Observer {
            binding.BrandView.layoutManager =
                LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.BrandView.adapter =
                BrandAdapter(it as MutableList<BrandModel>) { selectedBrand ->
                    if (selectedBrand != null) {
                        viewModel.filterItemsByBrand(selectedBrand) // Marka seçildiğinde filtreleme
                    } else {
                        viewModel.clearFilter() // Filtre temizleme
                    }
                }
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrand()
    }

    private fun initPupolar() {
        binding.progressBarRecommendations.visibility = View.VISIBLE
        viewModel.filteredPupolar.observe(this, Observer {
            binding.RecommendationView.layoutManager =
                GridLayoutManager(this@DashboardActivity, 2)
            binding.RecommendationView.adapter =
                PupolarAdapter(it as MutableList<ItemsModel>)
            binding.progressBarRecommendations.visibility = View.GONE
        })
        viewModel.loadPupolar()
    }

    private fun logOut(view: View) {
        binding.logoutBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Log out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    startActivity(Intent(this@DashboardActivity, IntroActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun usersEmailTxt() {
        val emailTxt = binding.userEmailTxt.text.toString()
        val currenUser = auth.currentUser
        if (currenUser != null) {
            val email = currenUser.email
            binding.userEmailTxt.text = email
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, CartActivity::class.java))
        }
        binding.cartBtnL.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, CartActivity::class.java))
        }
        binding.profileBtn.setOnClickListener {
            val intent = Intent(this@DashboardActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.profileBtnL.setOnClickListener {
            val intent = Intent(this@DashboardActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.FavButton.setOnClickListener {
            val intent = Intent(this@DashboardActivity, FavActivity::class.java)
            startActivity(intent)
        }
        binding.dashBtn.setOnClickListener {
            Toast.makeText(this, "You are already in Dashboard", Toast.LENGTH_SHORT).show()
        }
        binding.DashBtnL.setOnClickListener {
            Toast.makeText(this, "You are already in Dashboard", Toast.LENGTH_SHORT).show()

        }
        binding.DashButton.setOnClickListener {
            Toast.makeText(this, "You are already in Dashboard", Toast.LENGTH_SHORT).show()

        }

    }
}
