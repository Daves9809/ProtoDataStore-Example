package com.example.protoexample.ui.state

sealed interface MainEvents {
    data class AddUserButtonClick(
        val name: String,
        val email: String,
        val gender: String
    ): MainEvents
    object ClearUsersButtonClick: MainEvents
    data class SetNameEvent(val name: String): MainEvents
    data class SetEmailEvent(val email: String): MainEvents
    data class SetGenderEvent(val gender: String): MainEvents
}