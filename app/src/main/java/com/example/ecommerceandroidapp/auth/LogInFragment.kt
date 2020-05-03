package com.example.ecommerceandroidapp.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.ecommerceandroidapp.R
import com.example.ecommerceandroidapp.databinding.LogInFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment(),AuthListener {
private lateinit var binding:LogInFragmentBinding
    private lateinit var auth: FirebaseAuth
    companion object {
        fun newInstance() = LogInFragment()
    }

    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.log_in_fragment, container, false)
        auth=FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStarted() {
       binding.progressBar.visibility=View.VISIBLE

    }

    override fun onSuccess() {
        binding.progressBar.visibility=View.GONE

        //Navigate to home fragment and set isAuthenticated to true
        findNavController().navigate(R.id.action_logInFragment_to_navigation_home)
        isAuthenticated()

    }

    override fun onFailure(message: String) {
        binding.progressBar.visibility=View.VISIBLE
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    private fun isAuthenticated() {
        val sharedPreference = context?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference?.edit()
        editor?.putBoolean("isAuthenticated", true)
        editor?.apply()

    }
}
