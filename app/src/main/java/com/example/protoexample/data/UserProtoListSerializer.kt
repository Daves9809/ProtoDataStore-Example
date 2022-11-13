package com.example.protoexample.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.protoexample.UsersListProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UsersListProtoSerializer: Serializer<UsersListProto> {
    override val defaultValue: UsersListProto
        get() = UsersListProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UsersListProto {
        try {
            return UsersListProto.parseFrom(input)
        }catch (exception: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UsersListProto, output: OutputStream) {
        t.writeTo(output)
    }
}