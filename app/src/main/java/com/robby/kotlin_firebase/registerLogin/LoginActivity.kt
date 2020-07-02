package com.robby.kotlin_firebase.registerLogin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.robby.kotlin_firebase.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_account.setOnClickListener {
            loginAccount()
        }
        create_account.setOnClickListener {
            finish()
        }
    }

    private fun loginAccount(){
        val email = email_account.text.toString()
        val password = password_account.text.toString()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Enter Email & Password", Toast.LENGTH_SHORT).show()
            return
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("TEST", "Successfully created with user uid: ${it.result!!.user!!.uid}")
            }.addOnFailureListener {
                Log.d("TEST", "Failed Create account: ${it.message}")
            }
        }
    }
}