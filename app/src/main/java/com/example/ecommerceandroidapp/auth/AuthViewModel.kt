package com.example.ecommerceandroidapp.auth

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.ecommerceandroidapp.model.FirebaseSource
import com.example.ecommerceandroidapp.repository.FirebaseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Will communicate with our FreelancerRepository when a user needs to log in
 */
class AuthViewModel() : ViewModel() {
    private val source = FirebaseSource()
    private val repository: FirebaseRepository = FirebaseRepository(source)

    //email and password input
    var email: String? = null
    var password: String? = null
    var name: String? = null

    var authListener: AuthListener? = null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }


    //Log in with email and password
    fun loginWithGoogle() {
        //Validate email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        //Authentication has started
        authListener?.onStarted()

        //Therefore call the repository to perform the authentication
        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //Send success callback
                authListener?.onSuccess()
//                    firebaseUser.isAuthenticated=true

            },
                {
                    authListener?.onFailure(it.message!!)
                })
        disposables.add(disposable)

    }

    //Add the red outline as well as the check mark or wrong mark
    open fun isValidEmail(): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    open fun isValidName(): Boolean {
        return !TextUtils.isEmpty(name) && Patterns.EMAIL_ADDRESS.matcher(name).matches()
    }


    //Sign Up With Google and save name, email and password
    fun signUpWithGoogle() {

        if (email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }
        authListener?.onStarted()

        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
                repository.saveUserName(name!!, email!!, password!!)
//                firebaseUser.isAuthenticated=true
//                firebaseUser.isCreated=true
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}
