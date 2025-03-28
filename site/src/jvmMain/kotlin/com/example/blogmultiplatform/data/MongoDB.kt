package com.example.blogmultiplatform.data

import com.example.blogmultiplatform.models.Post
import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.util.Constants.DATABASE_NAME
import com.example.blogmultiplatform.util.Constants.MONGO_URI
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import org.litote.kmongo.coroutine.CoroutineDatabase
import com.varabyte.kobweb.api.init.InitApiContext
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


@InitApi
fun initMongoDB(context: InitApiContext) {
    System.setProperty(
        "org.litote.mongo.test.mapping.service",
        "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
    )
    context.data.add(MongoDB(context))
    println("Connexion reussie")
}

class MongoDB(private val context: InitApiContext) : MongoRepository {
    private val client = KMongo.createClient(MONGO_URI)
    private val database = client.getDatabase(DATABASE_NAME).coroutine
    private val userCollection = database.getCollection<User>("user")
    private val postCollection = database.getCollection<Post>("post")



    override suspend fun addPost(post: Post): Boolean {
        return postCollection.insertOne(post).wasAcknowledged()
    }


    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection
                .find(
                    Filters.and(
                        eq(User::username.name, user.username),
                        eq(User::password.name, user.password)
                    )
                ).first()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            null
        }
    }

    override suspend fun checkUserId(id: String): Boolean {
        return try {
            val documentCount = userCollection.countDocuments(eq("_id", id))
            documentCount > 0
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            false
        }
    }
}