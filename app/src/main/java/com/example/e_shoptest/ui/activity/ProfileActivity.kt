package com.example.e_shoptest.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ActivityCartBinding
import com.example.e_shoptest.databinding.ActivityProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        userEmailTxt()
        changePassword()
        changeLang()
        logoutOnProfile()
        initBottomMenu()
        whistlist()
        back()

    }
    fun whistlist(){
        binding.whstListBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, FavActivity::class.java))
        }
    }
    fun back(){
        binding.BackBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, DashboardActivity::class.java))
        }
    }
     fun initBottomMenu() {

        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, CartActivity::class.java))
        }
        binding.cartBtnL.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, CartActivity::class.java))
        }
        binding.profileBtn.setOnClickListener {
            Toast.makeText(this, "You are already in Profile", Toast.LENGTH_SHORT).show()
        }
        binding.profileBtnL.setOnClickListener {
            Toast.makeText(this, "You are already in Profile", Toast.LENGTH_SHORT).show()

        }
        binding.dashBtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, DashboardActivity::class.java)
            startActivity(intent)
        }
        binding.DashBtnL.setOnClickListener{
            val intent = Intent(this@ProfileActivity, DashboardActivity::class.java)
            startActivity(intent)
        }
        binding.DashButton.setOnClickListener {
            val intent = Intent(this@ProfileActivity, DashboardActivity::class.java)
            startActivity(intent)
        }
        binding.FavButton.setOnClickListener {
            val intent = Intent(this@ProfileActivity, FavActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changePassword () {
        binding.changePasswrdBtn.setOnClickListener {
            val userMail = binding.userEmailTxtOnProfile.text.toString()
            if (userMail.isNotEmpty()) {
                auth.sendPasswordResetEmail(userMail).addOnSuccessListener {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "404 Not Found..", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun userEmailTxt (){
        val currenUser = auth.currentUser
        if (currenUser != null) {
            val email = currenUser.email
            binding.userEmailTxtOnProfile.text = email
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }
    }
    private fun changeLang() {
        val language = R.string.language
        binding.changeLang.setOnClickListener {
            Toast.makeText(this, language , Toast.LENGTH_SHORT).show()
        }
    }
    private fun logoutOnProfile() {
        binding.logoutBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Log out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    startActivity(Intent(this@ProfileActivity, IntroActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}