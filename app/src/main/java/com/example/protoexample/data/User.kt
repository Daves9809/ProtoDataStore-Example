package com.example.protoexample.data

import kotlin.random.Random

data class User(
    val id: Int = Random.nextInt(0,1000),
    val name: String = "",
    val email: String = "",
    val gender: String = "male",
    val status: String = "not-married"
)
