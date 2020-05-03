package com.example.ecommerceandroidapp.model

import android.app.DownloadManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val ecommerceUser = firebaseFirestore.collection("user").limit(25)

    var next: DownloadManager.Query? = null

    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun saveFreelancerCredentials(
        name: String,
        email: String,
        password: String
    ) {

        val freelancer = HashMap<String, Any>()
        freelancer.put("name", name)
        freelancer.put("email", email)
        freelancer.put("password", password)

        firebaseFirestore.collection("UserData").document(firebaseAuth.currentUser!!.uid)
            .set(freelancer)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

}