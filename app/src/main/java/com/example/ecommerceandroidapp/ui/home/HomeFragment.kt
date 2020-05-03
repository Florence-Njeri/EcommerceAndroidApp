package com.example.ecommerceandroidapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.ecommerceandroidapp.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val sharedPreference = requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val isFirstTime =sharedPreference.getBoolean("isFirstTime",true)
        val isAuthenticated =sharedPreference.getBoolean("isFirstTime",true)


        if(isFirstTime){
            findNavController().navigate(R.id.action_navigation_home_to_signUpFragment)
        }

        if(!isAuthenticated && !isFirstTime){
            findNavController().navigate(R.id.action_navigation_home_to_logInFragment)
        }

        return root
    }
}
