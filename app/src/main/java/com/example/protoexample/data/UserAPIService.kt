package com.example.protoexample.data

import com.example.protoexample.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAPIService {
    @GET("/public/v2/users")
    suspend fun getUsersFromAPI(
        @Query("Authorization")
        apiKey: String =Constant.API_KEY
    ): Response<List<User>>
}