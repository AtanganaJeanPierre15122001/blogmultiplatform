@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

actual sealed class ApiListResponse {
    @Serializable
    @SerialName("idle")
    actual object Idle : ApiListResponse()

    @Serializable
    @SerialName("success")
    actual class Success(val data : List<PostWithoutDetails>) : ApiListResponse()

    @Serializable
    @SerialName("error")
    actual class Error(val message : String) : ApiListResponse()
}