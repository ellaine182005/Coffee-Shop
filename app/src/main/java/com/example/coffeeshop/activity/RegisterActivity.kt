package com.example.coffeeshop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop.databinding.ActivityRegisterBinding
import com.example.coffeeshop.model.LoginResponse
import com.example.coffeeshop.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {


    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            registerUser()
        }

        binding.loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun registerUser() {

        val name = binding.nameEdt.text.toString().trim()

        val email = binding.emailEdt.text.toString().trim()

        val password = binding.passwordEdt.text.toString().trim()

        val address = binding.addressEdt.text.toString().trim()

        val gender = binding.genderEdt.text.toString().trim()

        val age = binding.ageEdt.text.toString().trim()

        val phone = binding.phoneEdt.text.toString().trim()

        if (
            name.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty() ||
            address.isEmpty() ||
            gender.isEmpty() ||
            age.isEmpty() ||
            phone.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Fill all fields",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        RetrofitClient.instance.registerUser(
            name,
            email,
            password,
            address,
            gender,
            age.toInt(),
            phone
        ).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (
                    response.isSuccessful &&
                    response.body()?.success == true
                ) {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registered Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@RegisterActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()

                } else {

                    Toast.makeText(
                        this@RegisterActivity,
                        response.body()?.message ?: "Register Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<LoginResponse>,
                t: Throwable
            ) {

                Toast.makeText(
                    this@RegisterActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


}
