package com.example.loginpage_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginpage_project.databinding.SignuppageBinding
import com.google.firebase.auth.FirebaseAuth

class SignuppageActivity : AppCompatActivity() {

    lateinit var binding: SignuppageBinding
    lateinit var Auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignuppageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Auth = FirebaseAuth.getInstance()

        binding.btnSubmit.setOnClickListener {

            var Email = binding.emailSU.text.toString().trim()
            var password = binding.passwordSU.text.toString().trim()

            if (Email.isEmpty()) {
                binding.emailSU.error="Enter Your Email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordSU.error="Enter Your Password"
                return@setOnClickListener
            }


            Auth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {

                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            }
        }
        binding.LogInbtn.setOnClickListener {

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}