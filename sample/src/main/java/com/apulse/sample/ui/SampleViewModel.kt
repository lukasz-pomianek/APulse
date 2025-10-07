package com.apulse.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.sample.data.*
import com.apulse.sample.network.MockApi
import com.apulse.sample.network.SampleApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleApi: SampleApi,
    private val mockApi: MockApi
) : ViewModel() {
    
    private val _responses = MutableStateFlow<List<NetworkResponse>>(emptyList())
    val responses: StateFlow<List<NetworkResponse>> = _responses.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun getUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val startTime = System.currentTimeMillis()
                val response = sampleApi.getUsers()
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://jsonplaceholder.typicode.com/users",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = response.body()?.toString()?.length ?: 0
                    )
                )
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://jsonplaceholder.typicode.com/users",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun createUser() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val startTime = System.currentTimeMillis()
                val userRequest = CreateUserRequest(
                    name = "John Doe",
                    email = "john.doe@example.com",
                    phone = "+1-555-0123",
                    website = "johndoe.com"
                )
                val response = sampleApi.createUser(userRequest)
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "POST",
                        url = "https://jsonplaceholder.typicode.com/users",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = response.body()?.toString()?.length ?: 0
                    )
                )
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "POST",
                        url = "https://jsonplaceholder.typicode.com/users",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateUser() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val startTime = System.currentTimeMillis()
                val updateRequest = UpdateUserRequest(
                    name = "Jane Smith",
                    email = "jane.smith@example.com"
                )
                val response = sampleApi.updateUser(1, updateRequest)
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "PUT",
                        url = "https://jsonplaceholder.typicode.com/users/1",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = response.body()?.toString()?.length ?: 0
                    )
                )
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "PUT",
                        url = "https://jsonplaceholder.typicode.com/users/1",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteUser() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val startTime = System.currentTimeMillis()
                val response = sampleApi.deleteUser(1)
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "DELETE",
                        url = "https://jsonplaceholder.typicode.com/users/1",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = 0
                    )
                )
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "DELETE",
                        url = "https://jsonplaceholder.typicode.com/users/1",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun simulateAuth() {
        viewModelScope.launch {
            _isLoading.value = true
            
            // First make a login request with credentials
            try {
                val startTime = System.currentTimeMillis()
                
                // Simulate auth by posting to httpbin
                val response = mockApi.login(
                    LoginRequest(
                        username = "testuser@example.com",
                        password = "secretpassword123"
                    )
                )
                
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "POST",
                        url = "https://httpbin.org/auth/login",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = response.body()?.toString()?.length ?: 0
                    )
                )
                
                // Follow up with an authenticated request
                try {
                    val authStartTime = System.currentTimeMillis()
                    val authResponse = mockApi.getProfile("Bearer jwt-token-here-12345")
                    val authDuration = System.currentTimeMillis() - authStartTime
                    
                    addResponse(
                        NetworkResponse(
                            method = "GET",
                            url = "https://httpbin.org/auth/profile",
                            statusCode = authResponse.code(),
                            duration = authDuration,
                            responseSize = authResponse.body()?.toString()?.length ?: 0
                        )
                    )
                } catch (e: Exception) {
                    addResponse(
                        NetworkResponse(
                            method = "GET",
                            url = "https://httpbin.org/auth/profile",
                            statusCode = 0,
                            duration = 0,
                            responseSize = 0,
                            error = e.message
                        )
                    )
                }
                
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "POST",
                        url = "https://httpbin.org/auth/login",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun getLargeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val startTime = System.currentTimeMillis()
                val response = mockApi.getLargeData(2000) // Request large dataset
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://httpbin.org/data/large?size=2000",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = response.body()?.toString()?.length ?: 0
                    )
                )
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://httpbin.org/data/large?size=2000",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun triggerError() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val startTime = System.currentTimeMillis()
                val response = mockApi.triggerError(500) // Trigger 500 error
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://httpbin.org/error/500",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = response.body()?.toString()?.length ?: 0
                    )
                )
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://httpbin.org/error/500",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun testSensitiveData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val startTime = System.currentTimeMillis()
                
                // Make request with sensitive data that should be redacted
                val response = mockApi.getSensitiveData(
                    apiKey = "secret-api-key-12345",
                    ssn = "123-45-6789",
                    email = "john.doe@example.com"
                )
                
                val duration = System.currentTimeMillis() - startTime
                
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://httpbin.org/sensitive/data?ssn=123-45-6789&email=john.doe@example.com",
                        statusCode = response.code(),
                        duration = duration,
                        responseSize = response.body()?.toString()?.length ?: 0
                    )
                )
                
                // Follow with a payment request containing credit card data
                try {
                    val paymentStartTime = System.currentTimeMillis()
                    val paymentData = mapOf(
                        "creditCard" to "4532-1234-5678-9012",
                        "cvv" to "123",
                        "expiryDate" to "12/25",
                        "amount" to 99.99,
                        "currency" to "USD"
                    )
                    
                    val paymentResponse = mockApi.processPayment(paymentData)
                    val paymentDuration = System.currentTimeMillis() - paymentStartTime
                    
                    addResponse(
                        NetworkResponse(
                            method = "POST",
                            url = "https://httpbin.org/payment/process",
                            statusCode = paymentResponse.code(),
                            duration = paymentDuration,
                            responseSize = paymentResponse.body()?.toString()?.length ?: 0
                        )
                    )
                } catch (e: Exception) {
                    addResponse(
                        NetworkResponse(
                            method = "POST",
                            url = "https://httpbin.org/payment/process",
                            statusCode = 0,
                            duration = 0,
                            responseSize = 0,
                            error = e.message
                        )
                    )
                }
                
            } catch (e: Exception) {
                addResponse(
                    NetworkResponse(
                        method = "GET",
                        url = "https://httpbin.org/sensitive/data",
                        statusCode = 0,
                        duration = 0,
                        responseSize = 0,
                        error = e.message
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearResponses() {
        _responses.value = emptyList()
    }
    
    private fun addResponse(response: NetworkResponse) {
        _responses.value = _responses.value + response
    }
}