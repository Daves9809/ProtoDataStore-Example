package com.example.protoexample.ui

import androidx.lifecycle.viewModelScope
import com.example.protoexample.Constant
import com.example.protoexample.UsersListProto
import com.example.protoexample.data.User
import com.example.protoexample.data.UsersProtoRepository
import com.example.protoexample.ui.state.MainEffects
import com.example.protoexample.ui.state.MainEvents
import com.example.protoexample.ui.state.MainPartialState
import com.example.protoexample.ui.state.MainScreenState
import com.tomcz.ellipse.Processor
import com.tomcz.ellipse.common.processor
import com.tomcz.ellipse.common.toNoAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias MainScreenProcessor = Processor<MainEvents, MainScreenState, MainEffects>

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersProtoRepository: UsersProtoRepository
) : androidx.lifecycle.ViewModel() {

    val processor: MainScreenProcessor = processor(
        initialState = MainScreenState(),
        prepare = {
            usersProtoRepository.usersProtoFlow.map { convertUserListProtoToUserList(it) }
                .map { MainPartialState.SetList(list = it) }
        },
        onEvent = { event ->
            when (event) {
                is MainEvents.AddUserButtonClick -> flow {
                    addUser(event.name, event.email, event.gender)
                }
                is MainEvents.ClearUsersButtonClick ->
                    clearDataStoreUsers().toNoAction()

                is MainEvents.SetNameEvent -> flowOf (
                    MainPartialState.SetName(event.name)
                )
                is MainEvents.SetEmailEvent -> flowOf (
                    MainPartialState.SetEmail(event.email)
                )
                is MainEvents.SetGenderEvent -> flowOf (
                    MainPartialState.SetGender(event.gender)
                )
            }
        }
    )

    private fun convertUserListProtoToUserList(usersListProto: UsersListProto): List<User> {
        val size = usersListProto.usersProtoCount
        val list = mutableListOf<User>()
        for (i in 0 until size) {
            val newUser = User(
                id = usersListProto.getUsersProto(i).id.toIntOrNull() ?: 1,
                name = usersListProto.getUsersProto(i).name,
                email = usersListProto.getUsersProto(i).email,
                gender = usersListProto.getUsersProto(i).gender
            )
            list.add(newUser)
        }
        return list
    }

    private fun clearDataStoreUsers() {
        viewModelScope.launch {
            usersProtoRepository.clearDataStore()
        }
    }

    fun addUser(name: String, email: String, gender: String) {
        viewModelScope.launch {
            usersProtoRepository.insertUser(name, email, gender)
        }
    }
}

