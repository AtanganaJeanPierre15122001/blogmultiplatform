@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.blogmultiplatform.models

expect sealed class ApiListResponse {
    object Idle
    class Success
    class Error
}