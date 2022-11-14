package com.example.protoexample.data

import androidx.datastore.core.DataStore
import com.example.protoexample.UserProto
import com.example.protoexample.UsersListProto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.random.Random

class UsersProtoRepository @Inject constructor(
    private val dataStore: DataStore<UsersListProto>
) {
    //get data
    val usersProtoFlow: Flow<UsersListProto> = dataStore.data

    suspend fun insertUser(name: String, email: String, gender: String) {
        dataStore.updateData { currentUsers ->
            val user: UserProto =
                UserProto.newBuilder()
                    .setName(name)
                    .setEmail(email)
                    .setGender(gender)
                    .setStatus("not-married")
                    .setId(Random.nextInt(0,1000).toString())
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