package com.example.loginpage_project

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage_project.databinding.HomepageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomepageActivity : AppCompatActivity() {

    lateinit var binding: HomepageBinding
    lateinit var ref: DatabaseReference
    lateinit var storage: StorageReference
    lateinit var Uri: Uri
    var Image_Code = 12
    lateinit var SharedP: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ref = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference


        SharedP = getSharedPreferences("First Time", Context.MODE_PRIVATE)


        binding.YourImage.setOnClickListener {
            if (binding.selectPhoto != null) {
                binding.selectPhoto.visibility = View.INVISIBLE
            } else {
                binding.selectPhoto.visibility = View.VISIBLE
            }
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, Image_Code)
        }


        binding.btnSubmit.setOnClickListener {
            var FirstName = binding.FirstName.text.toString().trim()
            var LastName = binding.LastName.text.toString().trim()
            var Birth_Date = binding.BirthDate.text.toString().trim()
            var E_mail_Id = binding.EMailId.text.toString().trim()
            var Male = binding.Male.isChecked
            var Female = binding.Female.isChecked
            var Mobile_No = binding.MobileNo.text.toString().trim()
            var Sports = binding.Sports.isChecked
            var Game = binding.Game.isChecked
            var Music = binding.Music.isChecked
            var Key = ref.root.push().key



            if (FirstName.isEmpty()) {
                binding.FirstName.error = "Enter Your First-Name"
                return@setOnClickListener
            }
            if (LastName.isEmpty()) {
                binding.LastName.error = "Enter Your Last-Name"
                return@setOnClickListener
            }
            if (Birth_Date.isEmpty()) {
                binding.BirthDate.error = "Enter Your Birth-Date"
                return@setOnClickListener
            }
            if (E_mail_Id.isEmpty()) {
                binding.EMailId.error = "Enter Your E-mailId"
                return@setOnClickListener
            }
            if (Mobile_No.isEmpty()) {
                binding.MobileNo.error = "Enter Your Mobile-No"
                return@setOnClickListener
            }


            var data = DetailModel(
                Key!!,
                FirstName,
                LastName,
                Birth_Date,
                E_mail_Id,
                Male, Female,
                Mobile_No, Sports, Game, Music
            )
            ref.root.child("Personal Details").child(Key).setValue(data)


            //image storage

            var Select = storage.child("images/${Uri.lastPathSegment}.jpg")
            var Upload = Select.putFile(Uri)

            var url = Upload.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception.let {
                        throw it!!
                    }
                }
                Select.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var downloadUri = task.result
                    var key1 = ref.root.push().key
                    ref.root.child("Image").child(key1!!).child("image")
                        .setValue(downloadUri.toString())

                } else {

                }
            }
            Toast.makeText(this, "Successful", Toast.LENGTH_LONG)

        }
        binding.Logout.setOnClickListener {
            editor = SharedP.edit()
            editor.clear()
            editor.commit()


            val googleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("17398671441-tecv6pl92l9125er92vsfk8uc8885373.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
            var googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
            googleSignInClient.signOut()


            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == Image_Code) {
                Uri = data?.data!!
                binding.YourImage.setImageURI(Uri)
            }
        }
    }
}