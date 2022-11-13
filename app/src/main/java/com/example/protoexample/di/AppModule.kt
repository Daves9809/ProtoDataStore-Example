package com.example.protoexample.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.example.protoexample.UsersListProto
import com.example.protoexample.data.UsersListProtoSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val USERS_PROTO_NAME = "users_proto"
private const val DATA_STORE_FILE_NAME = "user_list_proto.pb"

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<UsersListProto> {
        return DataStoreFactory.create(
            serializer = UsersListProtoSerializer,
            produceFile = {appContext.getFileStreamPath(DATA_STORE_FILE_NAME)}
        )
    }
}