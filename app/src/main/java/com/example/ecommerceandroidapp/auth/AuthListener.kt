package com.example.ecommerceandroidapp.auth

/**
 * AuthListener here so that when the task is finished we can fire a callback
 * inside our Activity, to determine what happened to the login or signup operation.
 */
interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}