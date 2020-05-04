package com.example.ecommerceandroidapp.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.ecommerceandroidapp.MainActivity
import com.example.ecommerceandroidapp.R
import com.example.ecommerceandroidapp.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)

        binding.viewModel= viewModel
        viewModel.authListener = this

        auth = FirebaseAuth.getInstance()

        binding.imageView.setOnClickListener {

            //Navigate to forgot password screen

        }
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
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isAuthenticated() {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("isAuthenticated", true)
        editor.apply()

    }
}
