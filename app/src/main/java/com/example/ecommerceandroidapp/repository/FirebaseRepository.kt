package com.example.ecommerceandroidapp.repository

import com.example.ecommerceandroidapp.model.FirebaseSource

/**
 *  The repository that will communicate with our Firebase backend
 */
class FirebaseRepository(private val firebase: FirebaseSource) {
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun saveUserName(name: String, email: String, password:String) = firebase.saveFreelancerCredentials(name,email,password)

    fun currentUser() = firebase.currentUser()




    fun logout() = firebase.logout()
}