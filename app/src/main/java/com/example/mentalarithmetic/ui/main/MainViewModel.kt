package com.example.mentalarithmetic.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mentalarithmetic.data.User
import com.example.mentalarithmetic.data.UsersList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.*

class MainViewModel: ViewModel() {

        var user: User=User()
        var usersList: UsersList=UsersList()
        lateinit var bd: FirebaseDatabase
        lateinit var myUserRef: DatabaseReference
        lateinit var myUserListRef: DatabaseReference
        lateinit var auth: FirebaseAuth
        lateinit var UID:String
        var myHomework: MutableLiveData<String>? = MutableLiveData()
        lateinit var context: Context
        lateinit var sPref: SharedPreferences



    init {
        //getUser(intent.getStringExtra("USER_EMAIL").toString(),intent.getStringExtra("USER_PASS").toString())
        myHomework = getHomework(user.groupName.toString())

    }

    fun getHomework() = myHomework

        fun MainViewModel(context: Context, auth:FirebaseAuth){
            this.context=context
            this.auth=auth
            this.UID=auth.uid.toString()
        }

        fun getUser(email:String, password:String){
            if (email != null && password !=null) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                }.addOnSuccessListener{
                    //получение экземпляра пользователя из базы данных
                    bd = FirebaseDatabase.getInstance("https://mental-arithmetic-5a392-default-rtdb.europe-west1.firebasedatabase.app")
                    myUserRef = bd.getReference().child("mental").child("users").child(UID)
                    myUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            user = snapshot.getValue(User::class.java)!!
                            //Toast.makeText(applicationContext,user?.status, Toast.LENGTH_LONG).show()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(context,error.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }
                    .addOnFailureListener { exception ->
                        when(exception){
                            is FirebaseAuthInvalidUserException -> Toast.makeText(context,"Такая учетная запись не найдена или отключена", Toast.LENGTH_LONG).show()
                            is FirebaseAuthInvalidCredentialsException -> Toast.makeText(context,"Неверный пароль", Toast.LENGTH_LONG).show()
                            else -> Toast.makeText(context,"Что то пошло не так :( ", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        fun searchUser(searchName:String): UsersList? {
            bd = FirebaseDatabase.getInstance("https://mental-arithmetic-5a392-default-rtdb.europe-west1.firebasedatabase.app")
            myUserListRef = bd.getReference().child("mental").child("userList").child(searchName)
            myUserListRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    usersList = snapshot.getValue(UsersList::class.java)?: UsersList()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,error.message, Toast.LENGTH_LONG).show()
                }
            })
            if (usersList!=null)
            return usersList
            else return UsersList()
        }

    fun getHomework(groupName:String): MutableLiveData<String>? {
        bd = FirebaseDatabase.getInstance("https://mental-arithmetic-5a392-default-rtdb.europe-west1.firebasedatabase.app")
        myUserListRef = bd.getReference().child("mental").child("groupList").child(user.groupName).child("hamework")
        myUserListRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myHomework?.value = listOf(snapshot.getValue(String::class.java).toString()).toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message, Toast.LENGTH_LONG).show()
            }
        })
            return myHomework
    }

        override fun onCleared() {
        super.onCleared()
        auth.signOut()
        }
}