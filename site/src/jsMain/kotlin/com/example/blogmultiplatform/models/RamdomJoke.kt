package com.example.blogmultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class RamdomJoke(
    val id: Int,
    val joke: String
)
