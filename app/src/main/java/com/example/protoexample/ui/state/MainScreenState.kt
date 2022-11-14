package com.example.protoexample.ui.state

import com.example.protoexample.data.User

data class MainScreenState(
    val listOfUsers: List<User> = emptyList(),
    val name: String = "",
    val email: String = "",
    val gender: String = ""
)
