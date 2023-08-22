package com.example.loginpage_project

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.loginpage_project.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var Auth: FirebaseAuth
    lateinit var SharedP: SharedPreferences
    lateinit var editor: Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Auth = FirebaseAuth.getInstance()

//        SharedP = getSharedPreferences("First Time", Context.MODE_PRIVATE)
//        var emailShareP =SharedP.getString("Email","")
//        var PassworrdShareP =SharedP.getString("Password","")
//
//        if (emailShareP != "" && PassworrdShareP != "") {
//            var intent = Intent(this, HomepageActivity::class.java)
//            startActivity(intent)
//        }


        binding.btnSubmit.setOnClickListener {
            var Email = binding.email.text.toString().trim()
            var Password = binding.password.text.toString().trim()

            if (Email.isEmpty()) {
                binding.email.error = "Enter Your Email"
                return@setOnClickListener
            }
            if (Password.isEmpty()) {
                binding.password.error = "Enter Your Password"
                return@setOnClickListener
            }

            //login page one time show

            SharedP = getSharedPreferences("First Time", Context.MODE_PRIVATE)

                editor = SharedP.edit()
                editor.putString("Email",Email)
                editor.putString("Password",Password)
                editor.apply()
                editor.commit()

                var intent = Intent(this, HomepageActivity::class.java)
                startActivity(intent)
                finish()



            Auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {

                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            }





        }
        binding.SignUp.setOnClickListener {
            var intent = Intent(this, SignuppageActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.ForgetPassword.setOnClickListener {

            var Email = binding.email.text.toString()
            Auth.sendPasswordResetEmail(Email)

            Toast.makeText(this, "Check Your Mail", Toast.LENGTH_LONG).show()

        }


    }
}
