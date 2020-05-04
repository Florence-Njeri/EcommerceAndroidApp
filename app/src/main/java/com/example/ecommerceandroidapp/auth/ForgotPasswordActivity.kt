package com.example.ecommerceandroidapp.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ecommerceandroidapp.R
import com.example.ecommerceandroidapp.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
private lateinit var binding:ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)

        binding.buttonSend.setOnClickListener {
            if(emailIsvalid()){
                resetpassword()
            }

        }
    }

    private fun resetpassword() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(binding.editTextEmail.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Email sent.")
                }
            }
    }
    private fun emailIsvalid(): Boolean {

//TODO find out why isValid is always false
        var isValid = true
        if (!TextUtils.isEmpty(binding.editTextEmail.text.toString()) && Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString()).matches()) {
            isValid = true

        }
        else{
            isValid = false
            binding.editTextEmail.requestFocus()
            binding.editTextEmail.error = "Invalid email address"
        }



        return isValid
    }
}
