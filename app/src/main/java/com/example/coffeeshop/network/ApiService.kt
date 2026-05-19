package com.example.coffeeshop.network

import com.example.coffeeshop.model.ItemsModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.coffeeshop.model.LoginResponse
import retrofit2.http.Path
import com.example.coffeeshop.model.OrderModel

interface ApiService {


    @GET("menu")
    fun getMenu(): Call<List<ItemsModel>>

    @GET("menu/popular")
    fun getPopularMenu(): Call<List<ItemsModel>>

    @GET("menu/offers")
    fun getOffersMenu(): Call<List<ItemsModel>>

    // LOGIN
    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    // REGISTER
    @FormUrlEncoded
    @POST("register")
    fun registerUser(

        @Field("name") name: String,

        @Field("email") email: String,

        @Field("password") password: String,

        @Field("address") address: String,

        @Field("gender") gender: String,

        @Field("age") age: Int,

        @Field("phone") phone: String

    ): Call<LoginResponse>

    // CHECKOUT
    @FormUrlEncoded
    @POST("checkout")
    fun checkout(
        @Field("user_name") userName: String,
        @Field("items") items: String,
        @Field("total_price") totalPrice: Double
    ): Call<LoginResponse>

    // ORDERS
    @GET("orders")
    fun getAllOrders(): Call<List<OrderModel>>

    @GET("orders/{user_name}")
    fun getOrdersByUser(
        @Path("user_name") userName: String
    ): Call<List<OrderModel>>

    @FormUrlEncoded
    @POST("update-user")
    fun updateUser(
        @Field("id") id: Int,
        @Field("address") address: String,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("phone") phone: String
    ): Call<LoginResponse>

}
