package com.fingerprint.kotlin

import android.content.Context
import android.util.Log
import com.fingerprintjs.android.fpjs_pro.Configuration
import com.fingerprintjs.android.fpjs_pro.FingerprintJSFactory

class FingerPrint(context: Context) {
    // Initialization
    val factory = FingerprintJSFactory(context)
    val configuration = Configuration(
        apiKey = "your-public-api-key",
        region = Configuration.Region.US
    )

    val fpjsClient = factory.createInstance(
        configuration
    )

    var visitorId: String? = null
    var requestId: String? = null
    init {
        getVisitorId()
    }

    private fun getVisitorId() {
        fpjsClient.getVisitorId { result ->
            Log.d("--FINGERPRINT--", result.toString())
            visitorId = result.visitorId
            requestId = result.requestId
        }
    }
}