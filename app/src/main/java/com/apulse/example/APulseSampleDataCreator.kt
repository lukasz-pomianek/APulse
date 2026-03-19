package com.apulse.example

import android.content.Context
import android.util.Log
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.NetworkRequest
import com.apulse.data.model.RequestBody
import com.apulse.data.model.RequestHeaders
import com.apulse.data.model.ResponseBody
import com.apulse.data.model.ResponseHeaders
import com.apulse.service.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import java.util.*

/**
 * Helper class to create sample data for testing APulse
 */
object APulseSampleDataCreator {
    
    fun createSampleData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("APulseSample", "Starting sample data creation")
                val database = APulseDatabase.getDatabase(context)
                val sessionManager = SessionManager.getInstance(database)
                
                // Ensure we have a session
                val sessionId = sessionManager.getCurrentSessionId()
                
                Log.d("APulseSample", "Creating sample data for session: $sessionId")
                
                // Create sample requests
                createSampleRequests(database, sessionId)
                
                Log.d("APulseSample", "Sample data created successfully")
            } catch (e: Exception) {
                Log.e("APulseSample", "Failed to create sample data", e)
            }
        }
    }
    
    private suspend fun createSampleRequests(database: APulseDatabase, sessionId: String) {
        val now = Clock.System.now()
        
        Log.d("APulseSample", "Creating sample requests for session: $sessionId")
        
        // Sample GET request
        val getRequestId = UUID.randomUUID().toString()
        Log.d("APulseSample", "Creating GET request with ID: $getRequestId")
        val getRequest = NetworkRequest(
            id = getRequestId,
            sessionId = sessionId,
            url = "https://api.github.com/users/octocat",
            host = "api.github.com",
            path = "/users/octocat",
            method = "GET",
            startTime = now,
            endTime = Clock.System.now(),
            duration = 234L,
            protocol = "HTTP/2",
            statusCode = 200,
            statusMessage = "OK",
            requestSize = 0L,
            responseSize = 1256L
        )
        
        try {
            database.networkRequestDao().insertRequestSuspend(getRequest)
            Log.d("APulseSample", "Inserted GET request successfully")
        } catch (e: Exception) {
            Log.e("APulseSample", "Failed to insert GET request", e)
        }
        
        // Headers for GET request
        try {
            database.requestHeadersDao().insertHeadersSuspend(
                RequestHeaders(
                    id = UUID.randomUUID().toString(),
                    requestId = getRequestId,
                    headers = mapOf(
                        "Accept" to "application/json",
                        "User-Agent" to "APulse/1.0"
                    )
                )
            )
            Log.d("APulseSample", "Inserted GET request headers successfully")
        } catch (e: Exception) {
            Log.e("APulseSample", "Failed to insert GET request headers", e)
        }
        
        // Response body for GET request
        try {
            database.responseBodyDao().insertBodySuspend(
            ResponseBody(
                id = UUID.randomUUID().toString(),
                requestId = getRequestId,
                contentType = "application/json",
                bodyText = """{
  "login": "octocat",
  "id": 1,
  "name": "The Octocat",
  "company": "GitHub",
  "blog": "https://github.blog",
  "location": "San Francisco",
  "email": null,
  "bio": "There once was...",
  "public_repos": 8,
  "followers": 9000,
  "following": 9
}""",
                size = 1256L
            )
        )
            Log.d("APulseSample", "Inserted GET response body successfully")
        } catch (e: Exception) {
            Log.e("APulseSample", "Failed to insert GET response body", e)
        }
        
        // Sample POST request
        val postRequestId = UUID.randomUUID().toString()
        Log.d("APulseSample", "Creating POST request with ID: $postRequestId")
        val postRequest = NetworkRequest(
            id = postRequestId,
            sessionId = sessionId,
            url = "https://api.example.com/users",
            host = "api.example.com",
            path = "/users",
            method = "POST",
            startTime = Clock.System.now(),
            endTime = Clock.System.now(),
            duration = 432L,
            protocol = "HTTP/2",
            statusCode = 201,
            statusMessage = "Created",
            requestSize = 156L,
            responseSize = 89L
        )
        
        try {
            database.networkRequestDao().insertRequestSuspend(postRequest)
            Log.d("APulseSample", "Inserted POST request successfully")
        } catch (e: Exception) {
            Log.e("APulseSample", "Failed to insert POST request", e)
        }
        
        // Headers for POST request
        database.requestHeadersDao().insertHeadersSuspend(
            RequestHeaders(
                id = UUID.randomUUID().toString(),
                requestId = postRequestId,
                headers = mapOf(
                    "Content-Type" to "application/json",
                    "Authorization" to "Bearer eyJhbGciOiJIUzI1NiJ9..."
                )
            )
        )
        
        // Request body for POST request
        database.requestBodyDao().insertBodySuspend(
            RequestBody(
                id = UUID.randomUUID().toString(),
                requestId = postRequestId,
                contentType = "application/json",
                bodyText = """{
  "name": "John Doe",
  "email": "john@example.com",
  "role": "developer"
}""",
                size = 156L
            )
        )
        
        // Response body for POST request
        database.responseBodyDao().insertBodySuspend(
            ResponseBody(
                id = UUID.randomUUID().toString(),
                requestId = postRequestId,
                contentType = "application/json",
                bodyText = """{
  "id": 123,
  "created": true
}""",
                size = 89L
            )
        )
        
        // Sample error request
        val errorRequestId = UUID.randomUUID().toString()
        val errorRequest = NetworkRequest(
            id = errorRequestId,
            sessionId = sessionId,
            url = "https://api.example.com/protected",
            host = "api.example.com", 
            path = "/protected",
            method = "GET",
            startTime = Clock.System.now(),
            endTime = Clock.System.now(),
            duration = 156L,
            protocol = "HTTP/2",
            statusCode = 401,
            statusMessage = "Unauthorized",
            requestSize = 0L,
            responseSize = 67L,
            error = "HTTP 401 Unauthorized"
        )
        
        database.networkRequestDao().insertRequestSuspend(errorRequest)
        
        // Response body for error request
        database.responseBodyDao().insertBodySuspend(
            ResponseBody(
                id = UUID.randomUUID().toString(),
                requestId = errorRequestId,
                contentType = "application/json",
                bodyText = """{
  "error": "Unauthorized",
  "message": "Invalid token"
}""",
                size = 67L
            )
        )
        
        Log.d("APulseSample", "Created 3 sample network requests")
    }
}