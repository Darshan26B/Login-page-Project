package com.example.loginpage_project

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.loginpage_project.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var Auth: FirebaseAuth
    lateinit var SharedP: SharedPreferences
    lateinit var editor: Editor
    lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("17398671441-tecv6pl92l9125er92vsfk8uc8885373.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this@MainActivity, googleSignInOptions)
        Auth = FirebaseAuth.getInstance()

       SharedP = getSharedPreferences("First Time", Context.MODE_PRIVATE)
        var emailShareP =SharedP.getString("Email","")
        var PassworrdShareP =SharedP.getString("Password","")

        if (emailShareP != "" && PassworrdShareP != "") {
            var intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }


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

            Auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, HomepageActivity::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener {

                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            }

    //login page one time show

    editor = SharedP.edit()
    editor.putString("Email",Email)
    editor.putString("Password",Password)
    editor.apply()
    editor.commit()

//    var intent = Intent(this, HomepageActivity::class.java)
//    startActivity(intent)

        }

        binding.googleSignIn.setOnClickListener {
            var signInClient = googleSignInClient.signInIntent


            launcher.launch(signInClient)
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

    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            result ->

        if (result.resultCode == Activity.RESULT_OK) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(result.data)


            if (task.isSuccessful) {

                var  account: GoogleSignInAccount =task.result
                var credencial = GoogleAuthProvider.getCredential(account.idToken,null)
                Auth.signInWithCredential(credencial).addOnCompleteListener {



                    if (it.isSuccessful) {
                        Toast.makeText(this,"Done",Toast.LENGTH_LONG).show()


                        var intent = Intent(this, HomepageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this,"Failed1",Toast.LENGTH_LONG).show()
                    }
                }
            }

        } else {
            Toast.makeText(this, "Failed2", Toast.LENGTH_LONG).show()
        }
    }
}
