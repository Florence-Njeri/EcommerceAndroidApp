package com.example.ecommerceandroidapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecommerceandroidapp.auth.LogInActivity
import com.example.ecommerceandroidapp.auth.SignUpActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_shop, R.id.navigation_bag))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val sharedPreference =getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val isFirstTime =sharedPreference.getBoolean("isFirstTime",true)
        val isAuthenticated =sharedPreference.getBoolean("isFirstTime",true)


        if(isFirstTime){
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        if(!isAuthenticated && !isFirstTime){
            startActivity(Intent(this, LogInActivity::class.java))
        }

    }
}
