package com.example.blogmultiplatform.util

import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.models.UserWithoutPassword
import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.json.Json


suspend fun checkUserExistence(user: User): UserWithoutPassword? {
    return try {
        val result = window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )
        println("Réponse de l'API : ${result.toString()}")
        Json.decodeFromString<UserWithoutPassword>(result.toString())
    } catch (e: Exception) {
        println("CURRENT_USER")
        println(e.message)
        null
    }
}