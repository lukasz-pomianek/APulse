package com.apulse.sample.network

import com.apulse.sample.data.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SampleApi {
    
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
    
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>
    
    @POST("users")
    suspend fun createUser(@Body user: CreateUserRequest): Response<User>
    
    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body user: UpdateUserRequest
    ): Response<User>
    
    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>
    
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
    
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<Post>
}

interface MockApi {
    
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    
    @GET("auth/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfile>
    
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") refreshToken: String
    ): Response<LoginResponse>
    
    @GET("data/large")
    suspend fun getLargeData(
        @Query("size") size: Int = 1000
    ): Response<LargeDataResponse>
    
    @GET("error/{code}")
    suspend fun triggerError(@Path("code") statusCode: Int): Response<ApiError>
    
    @Multipart
    @POST("upload/image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ResponseBody>
    
    @GET("sensitive/data")
    suspend fun getSensitiveData(
        @Header("X-API-Key") apiKey: String,
        @Query("ssn") ssn: String? = null,
        @Query("email") email: String? = null
    ): Response<Map<String, String>>
    
    @POST("payment/process")
    suspend fun processPayment(
        @Body paymentData: Map<String, Any>
    ): Response<Map<String, String>>
}