package com.example.mentalarithmetic.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.view.View
import android.widget.Toast

import com.example.mentalarithmetic.R
import com.example.mentalarithmetic.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class LoginViewModel() : ViewModel() {

    lateinit var user:User
    lateinit var bd: FirebaseDatabase
    lateinit var userRef: DatabaseReference
    lateinit var snap:Any
    lateinit var UID:String




    fun getUserInfo(uid:String,password: String){
        bd= FirebaseDatabase.getInstance()
        userRef = bd.getReference("https://mental-arithmetic-5a392-default-rtdb.europe-west1.firebasedatabase.app/mental/users")
        .child(uid)
        userRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())

            }

        })

    }
/*
    fun loginDataChanged(username: String, password: String, rememberMe:Boolean) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }*/

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}