package com.example.blogmultiplatform.util

import com.example.blogmultiplatform.models.Post
import com.example.blogmultiplatform.models.RamdomJoke
import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.models.UserWithoutPassword
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.http
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
//import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date


suspend fun checkUserExistence(user: User): UserWithoutPassword? {
    println("Réponse de l'API : $user")
    println("Réponse de l'API : ${Json.encodeToString(user).encodeToByteArray()}")
    return try {
        val result = window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )
        println("Réponse de l'API : $result")
        result?.decodeToString()?.let { Json.decodeFromString<UserWithoutPassword>(it) }
    } catch (e: Exception) {
        println("CURRENT_USER")
        println(e.message)
        null
    }
}

suspend fun checkUserId(id : String): Boolean {
    return try {
        val result = window.api.tryPost(
            apiPath = "checkuserid",
            body = Json.encodeToString(id).encodeToByteArray()
        )
        result?.decodeToString()?.let { Json.decodeFromString<Boolean>(it) } ?: false
    } catch (e: Exception) {
        println(e.message.toString())
        false
    }

}

suspend fun fetchRamdomJoke(onComplete: (RamdomJoke) -> Unit){
    val date = localStorage["date"]
    if(date != null){
        val difference = (Date.now() - date.toDouble())
        val dayHasPassed = difference >= 86400000
        if (dayHasPassed){
            try {
                val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
                onComplete(Json.decodeFromString<RamdomJoke>(result))
                localStorage["date"] = Date.now().toString()
                localStorage["joke"] = result
            }catch (e : Exception){
                onComplete(RamdomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }
        }else{
            try {
                localStorage["joke"]?.let { Json.decodeFromString<RamdomJoke>(it) }?.let { onComplete(it) }
            } catch (e: Exception) {
                onComplete(RamdomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }
        }
    }else{

            try {
                val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
                onComplete(Json.decodeFromString<RamdomJoke>(result))
                localStorage["date"] = Date.now().toString()
                localStorage["joke"] = result
            }catch (e : Exception){
                onComplete(RamdomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }
    }
}

suspend fun addPost(post: Post): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "addpost",
            body = Json.encodeToString(post).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}