package com.example.protoexample.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.protoexample.data.User
import com.example.protoexample.ui.state.MainEvents
import com.tomcz.ellipse.common.collectAsState

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {

    val processor = mainViewModel.processor
    val name by processor.collectAsState { it.name }
    val email by processor.collectAsState { it.email }
    val gender by processor.collectAsState { it.gender }
    val listOfUsers by processor.collectAsState { it.listOfUsers }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Users from DataStore",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        LazyList(listOfUsers)
        Divider(modifier = Modifier.padding(10.dp))
        TextFields(name, email, gender)
        Buttons(name, email, gender)


    }
}

@Composable
fun Buttons(
    name: String,
    email: String,
    gender: String,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val processor = mainViewModel.processor
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                modifier = Modifier
                    .clip(RoundedCornerShape(4f))
                    .width(150.dp)
                    .padding(end = 5.dp),
                onClick = {
                    processor.sendEvent(MainEvents.AddUserButtonClick(name, email, gender))
                    processor.sendEvent(MainEvents.SetNameEvent(""))
                    processor.sendEvent(MainEvents.SetEmailEvent(""))
                    processor.sendEvent(MainEvents.SetGenderEvent(""))
                }) {
                Text(text = "Add user to  dataStore")
            }
            Button(modifier = Modifier
                .clip(RoundedCornerShape(4f))
                .width(150.dp),
                onClick = {
                    processor.sendEvent(MainEvents.GetUserFromOnlineService)
                }) {
                Text(text = "Get users from online service")
            }
        }
        Button(colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.White
        ), onClick = {
            processor.sendEvent(MainEvents.ClearUsersButtonClick)
        }) {
            Text(text = "Clear users in dataStore")
        }
    }

}

@Composable
fun TextFields(
    name: String,
    email: String,
    gender: String,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val processor = mainViewModel.processor

    TextField(label = {
        Text(text = "Name")
    }, value = name, onValueChange = {
        processor.sendEvent(MainEvents.SetNameEvent(it))
    })
    TextField(label = {
        Text(text = "Email")
    }, value = email, onValueChange = {
        processor.sendEvent(MainEvents.SetEmailEvent(it))
    })
    TextField(label = {
        Text(text = "Gender")
    }, value = gender,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange = {
            processor.sendEvent(MainEvents.SetGenderEvent(it))
        })
}

@Composable
fun LazyList(listOfUsers: List<User>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items(listOfUsers) { user ->
            UserItem(user)
        }
    }

}

@Composable
fun UserItem(user: User) {
    Card(
        elevation = 10.dp,
        backgroundColor = Color(0xFFE9E9E9),
        modifier = Modifier.padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Name = ${user.name}")
            Text(text = "Email = ${user.email}")
            Text(text = "Gender = ${user.gender}")
        }
    }
}
