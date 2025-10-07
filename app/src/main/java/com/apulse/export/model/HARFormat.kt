package com.apulse.export.model

import kotlinx.serialization.Serializable

/**
 * HTTP Archive (HAR) format data structures
 * Based on HAR 1.2 specification: http://www.softwareishard.com/blog/har-12-spec/
 */

@Serializable
data class HAR(
    val log: HARLog
)

@Serializable
data class HARLog(
    val version: String = "1.2",
    val creator: HARCreator,
    val entries: List<HAREntry>,
    val pages: List<HARPage>? = null,
    val comment: String? = null
)

@Serializable
data class HARCreator(
    val name: String = "APulse",
    val version: String = "1.0",
    val comment: String? = null
)

@Serializable
data class HARPage(
    val startedDateTime: String,
    val id: String,
    val title: String,
    val pageTimings: HARPageTimings,
    val comment: String? = null
)

@Serializable
data class HARPageTimings(
    val onContentLoad: Long? = null,
    val onLoad: Long? = null,
    val comment: String? = null
)

@Serializable
data class HAREntry(
    val pageref: String? = null,
    val startedDateTime: String,
    val time: Long,
    val request: HARRequest,
    val response: HARResponse,
    val cache: HARCache = HARCache(),
    val timings: HARTimings,
    val serverIPAddress: String? = null,
    val connection: String? = null,
    val comment: String? = null
)

@Serializable
data class HARRequest(
    val method: String,
    val url: String,
    val httpVersion: String = "HTTP/1.1",
    val headers: List<HARHeader>,
    val queryString: List<HARQueryParam>,
    val cookies: List<HARCookie> = emptyList(),
    val headersSize: Long = -1,
    val bodySize: Long = -1,
    val postData: HARPostData? = null,
    val comment: String? = null
)

@Serializable
data class HARResponse(
    val status: Int,
    val statusText: String,
    val httpVersion: String = "HTTP/1.1",
    val headers: List<HARHeader>,
    val cookies: List<HARCookie> = emptyList(),
    val content: HARContent,
    val redirectURL: String = "",
    val headersSize: Long = -1,
    val bodySize: Long = -1,
    val comment: String? = null
)

@Serializable
data class HARHeader(
    val name: String,
    val value: String,
    val comment: String? = null
)

@Serializable
data class HARQueryParam(
    val name: String,
    val value: String,
    val comment: String? = null
)

@Serializable
data class HARCookie(
    val name: String,
    val value: String,
    val path: String? = null,
    val domain: String? = null,
    val expires: String? = null,
    val httpOnly: Boolean? = null,
    val secure: Boolean? = null,
    val comment: String? = null
)

@Serializable
data class HARPostData(
    val mimeType: String,
    val text: String? = null,
    val params: List<HARParam> = emptyList(),
    val comment: String? = null
)

@Serializable
data class HARParam(
    val name: String,
    val value: String? = null,
    val fileName: String? = null,
    val contentType: String? = null,
    val comment: String? = null
)

@Serializable
data class HARContent(
    val size: Long,
    val compression: Long? = null,
    val mimeType: String,
    val text: String? = null,
    val encoding: String? = null,
    val comment: String? = null
)

@Serializable
data class HARCache(
    val beforeRequest: HARCacheState? = null,
    val afterRequest: HARCacheState? = null,
    val comment: String? = null
)

@Serializable
data class HARCacheState(
    val expires: String? = null,
    val lastAccess: String,
    val eTag: String,
    val hitCount: Int,
    val comment: String? = null
)

@Serializable
data class HARTimings(
    val blocked: Long? = null,
    val dns: Long? = null,
    val connect: Long? = null,
    val send: Long,
    val wait: Long,
    val receive: Long,
    val ssl: Long? = null,
    val comment: String? = null
)