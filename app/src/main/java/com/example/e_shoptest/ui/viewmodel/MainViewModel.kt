package com.example.e_shoptest.ui.viewmodel

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_shoptest.ui.model.BrandModel
import com.example.e_shoptest.ui.model.ItemsModel
import com.example.e_shoptest.ui.model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {
    private val firebaseDatabaseRef = FirebaseDatabase.getInstance().reference
    private val _banner = MutableLiveData<List<SliderModel>>()
    private val _brand = MutableLiveData<List<BrandModel>>()
    private val _pupolar = MutableLiveData<List<ItemsModel>>()
    private val _favs = MutableLiveData<List<ItemsModel>>()

    private val _filteredPupolar = MutableLiveData<List<ItemsModel>>() // Filtrelenmiş ürünler için LiveData eklendi
    private lateinit var databaseReference: DatabaseReference
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    val favs : LiveData<List<ItemsModel>> = _favs
    val banners: LiveData<List<SliderModel>> = _banner
    val brands: LiveData<List<BrandModel>> = _brand
    val pupolar: LiveData<List<ItemsModel>> = _pupolar
    val filteredPupolar: LiveData<List<ItemsModel>> = _filteredPupolar // Filtrelenmiş ürünlerin LiveData'sı

    fun loadBanner() {
        val bannerRef = firebaseDatabase.getReference("Banner")
        bannerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bannerList = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        bannerList.add(list)
                    }
                    _banner.value = bannerList
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadBrand() {
        val brandRef = firebaseDatabase.getReference("Category")
        brandRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val brandList = mutableListOf<BrandModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(BrandModel::class.java)
                    if (list != null) {
                        brandList.add(list)
                    }
                    _brand.value = brandList
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun favStatus() {
        val favRef = firebaseDatabase.getReference("Items") // 'Items' ya da uygun düğüm adı
        favRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<ItemsModel>()
                for (itemSnapshot in snapshot.children) {
                    val title = itemSnapshot.child("title").getValue(String::class.java)
                    val favStatus = itemSnapshot.child("fav").getValue(Boolean::class.java)
                    val id = itemSnapshot.key
                    val description = itemSnapshot.child("description").getValue(String::class.java)
//                    val picUrl = itemSnapshot.child("picUrl").getValue(String::class.java)
//                    val size = itemSnapshot.child("size").getValue(ArrayList::class.java)

                    // 'picUrl' alanını al
                    val picUrlSnapshot = itemSnapshot.child("picUrl")
                    val picUrlList = arrayListOf<String>()
                    for (picUrlSnapshotItem in picUrlSnapshot.children) {
                        val picUrl = picUrlSnapshotItem.getValue(String::class.java)
                        picUrl?.let { picUrlList.add(it) }
                    }

                    // 'size' alanını al
                    val sizeSnapshot = itemSnapshot.child("size")
                    val sizeList = arrayListOf<String>()
                    for (sizeSnapshotItem in sizeSnapshot.children) {
                        val size = sizeSnapshotItem.getValue(String::class.java)
                        size?.let { sizeList.add(it) }
                    }

                    val price = itemSnapshot.child("price").getValue(Double::class.java)
                    val rating = itemSnapshot.child("rating").getValue(Double::class.java)
                    val numberInCart = itemSnapshot.child("numberInCart").getValue(Int::class.java)
                    val brand = itemSnapshot.child("brand").getValue(String::class.java)

                    // Eğer 'fav' durumu true ise
                    if (favStatus == true) {
                        Log.d("Firebase", "Item with true fav status: $favStatus $title $id")

                        // `ItemsModel` nesnesi oluşturun ve listeye ekleyin
                        val item = ItemsModel(
                            title = title ?: "",
                            description = description ?: "",
                            picUrl = picUrlList,
                            size = sizeList,
                            price = price ?: 0.0,
                            rating = rating ?: 0.0,
                            numberInCart = numberInCart ?: 0,
                            brand = brand ?: "",
                            fav = favStatus
                        )
                        itemList.add(item)
                    }
                }
                // LiveData güncelleyin
                _favs.value = itemList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }

        //favRef.addValueEventListener(favListener)


    fun loadPupolar() {
        val brandRef = firebaseDatabase.getReference("Items")
        brandRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        itemList.add(list)
                    }
                    _pupolar.value = itemList
                    _filteredPupolar.value = itemList // Başlangıçta tüm ürünleri yükle

                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun filterFavs() {
        val filtredList =_favs.value?.filter { it.fav == true }
        _filteredPupolar.value = filtredList ?: emptyList()
    }

    fun filterItemsByBrand(brand: String) { // Marka filtreleme metodu eklendi
        val filteredList = _pupolar.value?.filter { it.brand == brand }
        _filteredPupolar.value = filteredList ?: emptyList()
    }
    fun clearFilter() { // Filtre temizleme metodu eklendi
        _filteredPupolar.value = _pupolar.value
    }

    fun filterPupolarByBrand(brand: String) {
        _pupolar.value?.let { items ->
            _filteredPupolar.value = items.filter { it.brand == brand } // Markaya göre ürünleri filtreleyip LiveData'ya atıyoruz
        }
    }


    fun loadFavs() {
        val itemsRef = firebaseDatabase.getReference("Items")
        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        itemList.add(list)
                    }
                    _favs.value = itemList
//                    _filteredPupolar.value = itemList // Başlangıçta tüm ürünleri yükle

                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

}
