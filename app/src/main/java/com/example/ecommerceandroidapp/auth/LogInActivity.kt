package com.example.ecommerceandroidapp.auth

import android.app.Activity
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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class LogInActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel
    private val RC_SIGN_IN: Int = 1
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

        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {

                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    // User logging in after log out, check if there are shared prefs

                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                } else {
                    //                    Log in to the main Activity
                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                }

            } else {
                Toast.makeText(this@LogInActivity, "Please try again", Toast.LENGTH_SHORT).show()
            }
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
