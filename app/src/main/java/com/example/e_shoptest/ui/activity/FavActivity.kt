package com.example.e_shoptest.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_shoptest.databinding.ActivityFavBinding
import com.example.e_shoptest.ui.adapter.FavAdapter
import com.example.e_shoptest.ui.model.ItemsModel
import com.example.e_shoptest.ui.viewmodel.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class FavActivity : BaseActivity() {
    private lateinit var binding: ActivityFavBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var database: DatabaseReference
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val favItemsList = MutableLiveData<List<ItemsModel>>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=
            ViewModelProvider(this).get(MainViewModel::class.java)

        database = Firebase.database.reference

        initBottomMenu()
       initFavs()
        back()
     //   favStatus()

    }

    fun back() {
        binding.BackBtn.setOnClickListener {
            startActivity(Intent(this@FavActivity, DashboardActivity::class.java))
        }
    }
//    private fun initFavs() {
//        viewModel.favs.observe(this, Observer {
//            binding.favList.layoutManager=
//            GridLayoutManager(this@FavActivity,2)
//            binding.favList.adapter =
//                FavAdapter(it as MutableList<ItemsModel>)
//        })
//        viewModel.loadFavs()
//    }
    fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@FavActivity, CartActivity::class.java))
        }
        binding.cartBtnL.setOnClickListener {
            startActivity(Intent(this@FavActivity, CartActivity::class.java))
        }
        binding.profileBtn.setOnClickListener {
            val intent = Intent(this@FavActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.profileBtnL.setOnClickListener {
            val intent = Intent(this@FavActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.FavButton.setOnClickListener {
            Toast.makeText(this, "You are already in Whist List", Toast.LENGTH_SHORT).show()

        }
        binding.dashBtn.setOnClickListener {
            val intent = Intent(this@FavActivity, DashboardActivity::class.java)
            startActivity(intent)        }
        binding.DashBtnL.setOnClickListener {
            val intent = Intent(this@FavActivity, DashboardActivity::class.java)

        }
        binding.DashButton.setOnClickListener {
            val intent = Intent(this@FavActivity, DashboardActivity::class.java)

        }

    }

    fun initFavs() {
        viewModel.favs.observe(this, Observer {
            binding.favList.layoutManager =
                GridLayoutManager(this@FavActivity, 2)
            binding.favList.adapter =
                FavAdapter(it as MutableList<ItemsModel>)
        })
        viewModel.favStatus()
    }
//    fun favStatus() {
//        val favRef = firebaseDatabase.getReference("Items") // 'Items' ya da uygun düğüm adı
//        val favListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // `snapshot` içerisinde tüm veriler bulunuyor
//                val itemList = mutableListOf<ItemsModel>()
//                for (itemSnapshot in snapshot.children) {
//                    val title= itemSnapshot.child("title").getValue(String::class.java)
//                    val favStatus = itemSnapshot.child("fav").getValue(Boolean::class.java)
//                    val id = itemSnapshot.key
//                    // Eğer 'fav' durumu true ise
//                    if (favStatus == true) {
//                        Log.d("Firebase", "Item with true fav status: $favStatus $title $id")
//                        // Burada 'fav' durumu true olan öğeyi işleyebilirsiniz
//                        favItemsList.value = itemList
//
//
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Hata durumunda işlem yapma
//                Log.e("Firebase", "Error: ${error.message}")
//            }
//        }
//
//        favRef.addValueEventListener(favListener)
//    }

}