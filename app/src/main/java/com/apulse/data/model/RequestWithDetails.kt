package com.apulse.data.model

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Serializable
data class RequestWithDetails(
    @Embedded 
    val request: NetworkRequest,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "requestId"
    )
    val requestHeaders: RequestHeaders?,
    
    @Relation(
        parentColumn = "id", 
        entityColumn = "requestId"
    )
    val requestBody: RequestBody?,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "requestId" 
    )
    val responseHeaders: ResponseHeaders?,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "requestId"
    )
    val responseBody: ResponseBody?
)