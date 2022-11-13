package com.example.protoexample.ui

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.example.protoexample.Constant
import com.example.protoexample.UserProto
import com.example.protoexample.UsersListProto
import com.example.protoexample.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val usersProtoRepository: UsersProtoRepository
) : androidx.lifecycle.ViewModel() {

    private val _flowUser = MutableStateFlow(listOf<User>())
    val flowUserList = _flowUser

    fun getUsers() {
        viewModelScope.launch {
            usersProtoRepository.usersProtoFlow.collect { userListProto ->
                val size = userListProto.usersProtoCount
                val list = mutableListOf<User>()
                for (i in 0 until size) {
                    val newUser = User(
                        id = userListProto.getUsersProto(i).id,
                        name = userListProto.getUsersProto(i).name,
                        email = userListProto.getUsersProto(i).email,
                        gender = userListProto.getUsersProto(i).gender
                    )
                    list.add(newUser)
                }
                _flowUser.update { list }
            }

        }
    }

    fun clearDataStoreUsers(){
        viewModelScope.launch {
            usersProtoRepository.clearDataStore()
        }
    }

    fun updateUsers() {
        viewModelScope.launch {
            usersProtoRepository.updateUsersList(Constant.protoUsers)
        }
    }

    fun addUser(name: String, email: String, gender: String) {
        viewModelScope.launch {
            usersProtoRepository.insertUser(name, email, gender)
        }
    }
}

class UsersProtoRepository @Inject constructor(
    private val dataStore: DataStore<UsersListProto>
) {
    //get data
    val usersProtoFlow: Flow<UsersListProto> = dataStore.data

    //writing data
    suspend fun updateUsersList(listOfUserProto: List<UserProto>) {
        dataStore.updateData { currentUsers ->
            currentUsers.toBuilder().addAllUsersProto(listOfUserProto).build()
        }
    }

    suspend fun insertUser(name: String, email: String, gender: String) {
        dataStore.updateData { currentUsers ->
            val user: UserProto =
                UserProto.newBuilder()
                    .setName(name)
                    .setEmail(email)
                    .setGender(gender)
                    .setStatus("0")
                    .setId("400")
                    .setStatus("status")
                    .build()
            currentUsers.toBuilder().addUsersProto(user).build()
        }
    }

    suspend fun clearDataStore(){
        dataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}

