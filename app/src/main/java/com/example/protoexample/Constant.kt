package com.example.protoexample

object Constant {
    val protoUsers: List<UserProto> = listOf(
        UserProto
            .newBuilder()
            .setId("1")
            .setEmail("wacek@op.pl")
            .setGender("Male")
            .setName("wacek")
            .setStatus("married")
            .build(),

        UserProto
            .newBuilder()
            .setId("2")
            .setEmail("Andrzej@op.pl")
            .setGender("Male")
            .setName("Andrzej")
            .setStatus("not-married")
            .build(),
    )
}