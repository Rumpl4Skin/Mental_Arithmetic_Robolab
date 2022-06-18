package com.example.mentalarithmetic.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mentalarithmetic.ui.main.MainActivity
import com.example.mentalarithmetic.data.User
import com.example.mentalarithmetic.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var  auth: FirebaseAuth

    lateinit var sPref:SharedPreferences
    lateinit var uID:String
    var user=User()
    lateinit var loading: ProgressBar

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        loading = binding.loading
        val rememberMe=binding.rememberMe
        val showPass= binding.showPass

        var passIsShow=false

        auth= FirebaseAuth.getInstance(FirebaseApp.getInstance())

//проверка на сохраненного пользователя
        sPref = getSharedPreferences("MENTAL", MODE_PRIVATE)
        if (sPref.getString("SAVED_EMAIL", "")!="" && sPref.getString("SAVED_PASS", "")!="") {
            loginFirebase(sPref.getString("SAVED_EMAIL", ""), sPref.getString("SAVED_PASS", ""))
        }
        else
            Toast.makeText(applicationContext,"Сохраненные данные для входа не найдены! Введите логин и пароль", Toast.LENGTH_SHORT).show()


       binding.showPass?.setOnClickListener {
           if (binding.showPass!!.isChecked)
               binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
           else binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
           binding.password.setSelection(binding.password.text.toString().length)
       }

        username.apply{
            afterTextChanged {
            if (!isUserNameValid(binding.username.text.toString()))
                binding.username.error="Некорректный email!"
                binding.login.isEnabled = isPasswordValid(binding.password.text.toString()) and isUserNameValid(binding.username.text.toString())
        }
        }

        password.apply {
            afterTextChanged {

                if (!isPasswordValid(it))
                binding.password.error="Пароль должен быть длиннее 5ти символов!"

                binding.login.isEnabled = isPasswordValid(binding.password.text.toString()) and isUserNameValid(binding.username.text.toString())
            }

            /*setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }*/


        }

        login.setOnClickListener {
            loginFirebase(binding.username.text.toString(),binding.password.text.toString())
                // loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }


   /* private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }*/

    private fun loginFirebase(email: String?, password: String?){
        val intent = Intent(this, MainActivity::class.java)
        loading.visibility = View.VISIBLE
        if (email != null && password !=null) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->



            }.addOnSuccessListener{
                // loginViewModel.getUserInfo(binding.username.text.toString(),binding.password.text.toString())
                loading.visibility = View.GONE

    //получение экземпляра пользователя из базы данных

                uID= auth.uid.toString()
                var bd= FirebaseDatabase.getInstance("https://mental-arithmetic-5a392-default-rtdb.europe-west1.firebasedatabase.app")
                val myUserRef =bd.getReference().child("mental").child("users").child(uID)
                myUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        user = snapshot.getValue(User::class.java)!!

                        intent.apply {
                            putExtra("USER_PASS", binding.password.text.toString())
                            putExtra("USER_EMAIL", binding.username.text.toString())
                            putExtra("USER_ROLE", user.status)
                        }

                        if (binding.rememberMe!!.isChecked) {
                            sPref = getSharedPreferences("MENTAL", MODE_PRIVATE)
                            val ed: Editor = sPref.edit()
                            ed.putString("SAVED_EMAIL", binding.username.text.toString())
                            ed.putString("SAVED_PASS", binding.password.text.toString())
                            ed.commit()
                        }
                        Toast.makeText(applicationContext, "Успешный вход", Toast.LENGTH_SHORT).show()
                        loading.visibility = View.GONE
                        startActivity(intent)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext,error.message, Toast.LENGTH_LONG).show()
                    }

                })


            }
                .addOnFailureListener { exception ->
                    when(exception){
                        is FirebaseAuthInvalidUserException -> Toast.makeText(applicationContext,"Такая учетная запись не найдена или отключена", Toast.LENGTH_LONG).show()
                        is FirebaseAuthInvalidCredentialsException -> Toast.makeText(applicationContext,"Неверный пароль", Toast.LENGTH_LONG).show()
                        else -> Toast.makeText(applicationContext,"Что то пошло не так :( ", Toast.LENGTH_LONG).show()
                    }
                    loading.visibility = View.GONE
                }
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }



// A placeholder username validation check
private fun isUserNameValid(username: String): Boolean {
    return username.contains('@')
}

// A placeholder password validation check
private fun isPasswordValid(password: String): Boolean {
    return password.length > 5
}

private fun isUserSaved(): Boolean {
    if(sPref.contains("SAVED_EMAIL")){
    val intent = Intent(this, MainActivity::class.java).apply {
        putExtra("USER_PASS", binding.password.text.toString())
        putExtra("USER_EMAIL", binding.username.text.toString())
    }
    }
    return true
}
/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
}