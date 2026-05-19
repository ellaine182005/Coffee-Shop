package com.example.coffeeshop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop.databinding.ActivityLoginBinding
import com.example.coffeeshop.model.LoginResponse
import com.example.coffeeshop.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            loginUser()
        }

        binding.registerTxt.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {

        val email = binding.emailEdt.text.toString().trim()
        val password = binding.passwordEdt.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitClient.instance.loginUser(email, password)
            .enqueue(object : Callback<LoginResponse> {

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    if (response.isSuccessful && response.body()?.success == true) {

                        val user = response.body()?.user

                        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
                        val editor = sharedPref.edit()

                        editor.putInt("id", user?.id ?: 0)
                        editor.putString("name", user?.name ?: "")
                        editor.putString("email", user?.email ?: "")
                        editor.apply()

                        Toast.makeText(
                            this@LoginActivity,
                            "Login Success",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()

                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid login", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}