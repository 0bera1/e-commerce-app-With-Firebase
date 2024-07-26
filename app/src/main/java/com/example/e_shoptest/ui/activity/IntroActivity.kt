package com.example.e_shoptest.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ActivityIntroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.squareup.picasso.Picasso

class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var viewBinding: ActivityIntroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this@IntroActivity, DashboardActivity::class.java))
        }else {
            binding.getStartedButton.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
        }

           // val imageView = findViewById<ImageView>(R.id.imageView4) // ImageView'ınızın ID'sini buraya yazın
           // Picasso.get().load(R.drawable.intro_logo2).into(imageView)


        }

    }
}