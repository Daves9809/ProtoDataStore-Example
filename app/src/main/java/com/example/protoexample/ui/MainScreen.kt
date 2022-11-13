package com.example.protoexample.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.protoexample.data.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@Composable
fun MainScreen(viewModel: ViewModel = hiltViewModel()) {

    val name = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val gender = remember {
        mutableStateOf("")
    }

    val usersFromDataStore = viewModel.flowUserList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(label = {
            Text(text = "Name")
        }, value = name.value, onValueChange = {
            name.value = it
        })
        TextField(label = {
            Text(text = "Email")
        }, value = email.value, onValueChange = {
            email.value = it
        })
        TextField(label = {
            Text(text = "Gender")
        }, value = gender.value, onValueChange = {
            gender.value = it
        })
        Row(modifier = Modifier.fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

            Button(onClick = {
                viewModel.addUser(name.value,email.value,gender.value)
            }) {
                Text(text = "Add user to  dataStore")
            }
            Button(onClick = {
                viewModel.updateUsers()
            }) {
                Text(text = "Init list to dataStore")
            }
        }
        Divider()
        Text(text = "Users from DataStore")

        Row(modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                viewModel.getUsers()
            }) {
                Text(text = "Get user from DataStore")
            }
            Button(onClick = {
                viewModel.clearDataStoreUsers()
            }) {
                Text(text = "Clear users in dataStore")
            }
        }
        LazyColumn{
            items(usersFromDataStore.value){ user ->
                UserItem(user)
            }
        }


    }
}

@Composable
fun UserItem(user: User) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Name = ${user.name}")
        Text(text = "Email = ${user.email}")
        Text(text = "Gender = ${user.gender}")
        Text(text = "Status = ${user.status}")
    }
}
