package com.example.protoexample.data

import retrofit2.Response
import javax.inject.Inject

class UsersServiceRepository @Inject constructor(
    private val userAPIService: UserAPIService
) {
    suspend fun getUsersFromAPI(): Response<List<User>>{
        return userAPIService.getUsersFromAPI()
    }
}