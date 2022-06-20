package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramclone.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        signUp()
        signIn()

        if (auth.currentUser != null) {
            startActivity(Intent(this, FeedActivity::class.java))
            finish()
        }

    }


    private fun signUp() {
        binding.signUpButton.setOnClickListener {

            val login = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (login.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(login, password).addOnSuccessListener {
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()

                }.addOnFailureListener {

                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()

                }


            } else {
                Toast.makeText(this, "Enter email  and password", Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun signIn() {
        binding.signInButton.setOnClickListener {


            val login = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (login.isNotEmpty() && password.isNotEmpty()) {

                auth.signInWithEmailAndPassword(login, password).addOnSuccessListener {
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }


            } else {

                Toast.makeText(this, "Enter email and password", Toast.LENGTH_LONG).show()
            }


        }

    }


}






































