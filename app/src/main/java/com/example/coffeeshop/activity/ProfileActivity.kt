package com.example.coffeeshop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop.databinding.ActivityProfileBinding
import com.example.coffeeshop.model.LoginResponse
import com.example.coffeeshop.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadUserData()

        binding.saveBtn.setOnClickListener {
            updateProfile()
        }

        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun loadUserData() {
        val pref = getSharedPreferences("UserSession", MODE_PRIVATE)

        binding.nameTxt.text = pref.getString("name", "Coffee Lover")
        binding.emailTxt.text = pref.getString("email", "No Email")

        binding.addressEdt.setText(pref.getString("address", ""))
        binding.genderEdt.setText(pref.getString("gender", ""))
        binding.ageEdt.setText(pref.getInt("age", 0).toString())
        binding.phoneEdt.setText(pref.getString("phone", ""))
    }

    private fun updateProfile() {

        val pref = getSharedPreferences("UserSession", MODE_PRIVATE)
        val id = pref.getInt("id", 0)

        val address = binding.addressEdt.text.toString()
        val gender = binding.genderEdt.text.toString()
        val age = binding.ageEdt.text.toString().toIntOrNull() ?: 0
        val phone = binding.phoneEdt.text.toString()

        RetrofitClient.instance.updateUser(
            id,
            address,
            gender,
            age,
            phone
        ).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Profile Updated",
                    Toast.LENGTH_SHORT
                ).show()

                val editor = pref.edit()
                editor.putString("address", address)
                editor.putString("gender", gender)
                editor.putInt("age", age)
                editor.putString("phone", phone)
                editor.apply()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun logout() {
        val pref = getSharedPreferences("UserSession", MODE_PRIVATE)
        pref.edit().clear().apply()

        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }
}