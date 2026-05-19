package com.example.coffeeshop.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeeshop.model.ItemsModel
import com.example.coffeeshop.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    // =========================
    // POPULAR
    // =========================

    private val _popular = MutableLiveData<List<ItemsModel>>()
    val popular: LiveData<List<ItemsModel>> = _popular

    // =========================
    // OFFERS
    // =========================

    private val _offers = MutableLiveData<List<ItemsModel>>()
    val offers: LiveData<List<ItemsModel>> = _offers

    // =========================
    // LOAD POPULAR
    // =========================

    fun loadPopular() {

        RetrofitClient.instance.getPopularMenu()
            .enqueue(object : Callback<List<ItemsModel>> {

                override fun onResponse(
                    call: Call<List<ItemsModel>>,
                    response: Response<List<ItemsModel>>
                ) {

                    if (response.isSuccessful) {

                        val body = response.body()

                        Log.d("POPULAR_DATA", body.toString())

                        if (!body.isNullOrEmpty()) {

                            val itemList = body.map {

                                ItemsModel(
                                    id = it.id,
                                    name = it.name,
                                    image = it.image,
                                    title = it.name,
                                    description = it.description,
                                    price = it.price,
                                    picUrl = arrayListOf(
                                        "http://192.168.0.107:3000/uploads/${it.image}"
                                    ),
                                    rating = 4.5,
                                    extra = "Popular Coffee"
                                )
                            }

                            _popular.postValue(itemList)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ItemsModel>>, t: Throwable) {

                    Log.e("POPULAR_ERROR", t.message.toString())
                }
            })
    }

    // =========================
    // LOAD OFFERS
    // =========================

    fun loadOffers() {

        RetrofitClient.instance.getOffersMenu()
            .enqueue(object : Callback<List<ItemsModel>> {

                override fun onResponse(
                    call: Call<List<ItemsModel>>,
                    response: Response<List<ItemsModel>>
                ) {

                    if (response.isSuccessful) {

                        val body = response.body()

                        Log.d("OFFERS_DATA", body.toString())

                        if (!body.isNullOrEmpty()) {

                            val itemList = body.map {

                                ItemsModel(
                                    id = it.id,
                                    name = it.name,
                                    image = it.image,
                                    title = it.name,
                                    description = it.description,
                                    price = it.price,
                                    picUrl = arrayListOf(
                                        "http://192.168.0.107:3000/uploads/${it.image}"
                                    ),
                                    rating = 4.5,
                                    extra = "Special Offer"
                                )
                            }

                            _offers.postValue(itemList)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ItemsModel>>, t: Throwable) {

                    Log.e("OFFERS_ERROR", t.message.toString())
                }
            })
    }
}