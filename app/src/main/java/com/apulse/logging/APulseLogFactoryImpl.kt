package com.apulse.logging

import android.content.Context
import com.apulse.core.APulseConfig
import com.apulse.core.APulseLogFactory
import com.apulse.core.APulseLogInterface
import com.apulse.capture.log.APulseLogInterceptor

/**
 * Factory implementation that creates the actual APulse log interceptor
 * This is in the main app module which has access to all dependencies
 */
class APulseLogFactoryImpl : APulseLogFactory {
    
    override fun createLogInterceptor(context: Context, config: APulseConfig, excludedTags: List<String>): APulseLogInterface {
        // Return the singleton instance of APulseLogInterceptor with excluded tags
        return APulseLogInterceptor.getInstance(context, excludedTags)
    }
}