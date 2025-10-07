package com.apulse.sample.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String? = null,
    val website: String? = null,
    val company: Company? = null
)

@Serializable
data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val user: UserProfile
)

@Serializable
data class UserProfile(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)

@Serializable
data class CreateUserRequest(
    val name: String,
    val email: String,
    val phone: String? = null,
    val website: String? = null
)

@Serializable
data class UpdateUserRequest(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val website: String? = null
)

@Serializable
data class ApiError(
    val error: String,
    val message: String,
    val statusCode: Int
)

@Serializable
data class LargeDataResponse(
    val data: List<String>,
    val metadata: Map<String, String>,
    val timestamp: Long
)