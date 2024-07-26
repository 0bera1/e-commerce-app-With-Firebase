package com.example.e_shoptest.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container,
            false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GoRegister()
        GoDashboard()
        refreshPassword()


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun GoRegister(){
        binding.goRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.logintoregister)
        }
    }
    fun GoDashboard(){
        binding.loginButton.setOnClickListener {
            login(it)
//            Navigation.findNavController(it).navigate(R.id.goDash)
        }
    }
    private fun login(view: View) {
        val email = binding.loginTextEmail.text.toString()
        val password = binding.loginTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    // Giriş başarılı olduktan sonra currentUser'i kontrol et
                    val userVerify = auth.currentUser
                    if (userVerify?.isEmailVerified == true) {
                        findNavController().navigate(R.id.goDash)
                    } else {
                        Toast.makeText(context, "Please verify your email", Toast.LENGTH_LONG).show()
                        // İsteğe bağlı: Kullanıcıya e-posta doğrulama gönder
                        userVerify?.sendEmailVerification()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                    Log.e("login", it.localizedMessage.toString())
                }
        } else {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
        }
    }
    private fun refreshPassword() {
        binding.forgotPassword.setOnClickListener {
            Toast.makeText(context,"Please enter your email address.",Toast.LENGTH_SHORT).show()
            val userEmail = binding.loginTextEmail.text.toString()
            if (userEmail.isNotEmpty()) {
                auth.sendPasswordResetEmail(userEmail).addOnCompleteListener { task ->
                    //Şifre sıfırlama e postası başarıyla gönderildi
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Password reset email sent.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        //Hata durumunda işlemleri
                        Toast.makeText(
                            requireContext(),
                            task.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter your email address!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}