package com.apulse.ui.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.RequestWithDetails
import com.apulse.data.repository.APulseRepository
import com.apulse.service.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RequestDetailViewModel(
    private val repository: APulseRepository
) : ViewModel() {
    
    private val _requestDetails = MutableStateFlow<RequestWithDetails?>(null)
    val requestDetails: StateFlow<RequestWithDetails?> = _requestDetails.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun loadRequest(requestId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val networkRequestDetails = repository.getRequestDetailsSafe(requestId)
                
                if (networkRequestDetails != null) {
                    // Convert NetworkRequestDetails to RequestWithDetails
                    val requestWithDetails = RequestWithDetails(
                        request = networkRequestDetails.request,
                        requestHeaders = networkRequestDetails.requestHeaders,
                        requestBody = networkRequestDetails.requestBody,
                        responseHeaders = networkRequestDetails.responseHeaders,
                        responseBody = networkRequestDetails.responseBody
                    )

                    _requestDetails.value = requestWithDetails
                } else {
                    android.util.Log.w("RequestDetailViewModel", "Request with ID $requestId not found")
                    _requestDetails.value = null
                }
            } catch (e: Exception) {
                android.util.Log.e("RequestDetailViewModel", "Error loading request details for ID $requestId", e)
                _requestDetails.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}