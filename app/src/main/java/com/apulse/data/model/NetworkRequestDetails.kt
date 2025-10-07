package com.apulse.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkRequestDetails(
    val request: NetworkRequest,
    val requestHeaders: RequestHeaders? = null,
    val responseHeaders: ResponseHeaders? = null,
    val requestBody: RequestBody? = null,
    val responseBody: ResponseBody? = null,
    val appMetadata: AppMetadata? = null
)