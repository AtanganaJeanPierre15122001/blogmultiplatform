package com.example.blogmultiplatform.data

import com.example.blogmultiplatform.models.User
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import org.litote.kmongo.MongoOperator

class MongoDB : MongoRepository {


    @InitApi
    fun initMongoDB(context: InitApiContext) {
        System.setProperty(
            "org.litote.mongo.test.mapping.service",
            "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
        )
        context.data.add(MongoDB())
    }


    override suspend fun checkUserExistence(user: User): User? {
        TODO("Not yet implemented")
    }
}