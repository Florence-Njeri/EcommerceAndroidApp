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
import com.example.ecommerceandroidapp.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStarted() {
        binding.progressBar.visibility = View.VISIBLE

    }

    override fun onSuccess() {
        binding.progressBar.visibility = View.GONE

        //Navigate to home fragment and set isAuthenticated to true
        startActivity(Intent(this, MainActivity::class.java))
        isAuthenticated ()

    }

    override fun onFailure(message: String) {
        binding.progressBar.visibility = View.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isAuthenticated() {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("isAuthenticated", true)
        editor.apply()

    }
}
