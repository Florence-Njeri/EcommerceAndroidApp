package com.example.ecommerceandroidapp.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ecommerceandroidapp.MainActivity
import com.example.ecommerceandroidapp.R
import com.example.ecommerceandroidapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        binding.imageView.setOnClickListener {

            startActivity(Intent(this,LogInActivity::class.java))

        }
    }


    override fun onStarted() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        binding.progressBar.visibility = View.GONE

        //Navigate to Home Fragment and set authenticated to  and set is First Time false
        isAuthenticated()

        startActivity(Intent(this, MainActivity::class.java))
        isFirstTime()
    }

    override fun onFailure(message: String) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isAuthenticated() {
        val sharedPreference =
            getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("isAuthenticated", true)
        editor.apply()

    }

    private fun isFirstTime() {
        val sharedPreference =
            getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("isFirstTime", false)
        editor.apply()
    }
}
