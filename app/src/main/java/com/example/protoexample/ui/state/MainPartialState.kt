package com.example.protoexample.ui.state

import com.example.protoexample.data.User
import com.tomcz.ellipse.PartialState

sealed interface MainPartialState: PartialState<MainScreenState>{
    data class SetList(private val list: List<User>): MainPartialState{
        override fun reduce(oldState: MainScreenState): MainScreenState {
            return oldState.copy(listOfUsers = list)
        }
    }
    data class SetName(private val value: String): MainPartialState{
        override fun reduce(oldState: MainScreenState): MainScreenState {
            return oldState.copy(name = value)
        }
    }
    data class SetEmail(private val value: String): MainPartialState{
        override fun reduce(oldState: MainScreenState): MainScreenState {
            return oldState.copy(email = value)
        }
    }
    data class SetGender(private val value: String): MainPartialState{
        override fun reduce(oldState: MainScreenState): MainScreenState {
            return oldState.copy(gender = value)
        }
    }

}