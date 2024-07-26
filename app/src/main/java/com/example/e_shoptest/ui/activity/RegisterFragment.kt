package com.example.e_shoptest.ui.activity

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.FragmentRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterFragment : Fragment() {
    fun EditText.setEmailInputFilter() {
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container,false)
        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerBtn.setOnClickListener {
            register(it)
        }
        binding.loginAgain.setOnClickListener {
            findNavController().navigate(R.id.goBacktoLogin)
        }
    }
    fun register(view: View) {
        val email = binding.registerTextEmail.text.toString().trim()
        val password = binding.registerTextPassword.text.toString()
        val confirmPassword = binding.registerconfirmText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
            if (password.length < 8){
                Toast.makeText(context,"Password must be at least 8 characters.", Toast.LENGTH_LONG).show()
                return
            }else {
                if (password == confirmPassword){
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        // Doğrulama e-postası başarıyla gönderildi
                                        Toast.makeText(context,"Verification email sent.", Toast.LENGTH_LONG).show()
                                    } else {
                                        // Doğrulama e-postası gönderilemedi
                                        Toast.makeText(context,"Failed to send verification email.", Toast.LENGTH_LONG).show()
                                    }
                                }
                            // Kullanıcı oluşturuldu
                            findNavController().navigate(R.id.goBacktoLogin)
                        }

                    }.addOnFailureListener { exception ->
                        // Hata durumunda işlemleri
//                    Toast.makeText(requireContext(), exception.localizedMessage, Tast.LENGTH_LONG).show()
                        Toast.makeText(context,"Incorrect email address or password.", Toast.LENGTH_LONG).show()

                    }
                } else {
                    Toast.makeText(context,"Passwords do not match.", Toast.LENGTH_LONG).show()
                }
            }

        } else {
            Toast.makeText(context,"Please fill in all fields.", Toast.LENGTH_LONG).show()
            }


        }

}