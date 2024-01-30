package com.fingerprint.kotlin

import android.content.Context
import android.util.Log
import com.fingerprintjs.android.fpjs_pro.Configuration
import com.fingerprintjs.android.fpjs_pro.FingerprintJSFactory

class FingerPrint(context: Context) {
    // Initialization
    val factory = FingerprintJSFactory(context)
    val configuration = Configuration(
        apiKey = "tste7rSbQc9NsIliFwN8",
        region = Configuration.Region.EU
    )

    val fpjsClient = factory.createInstance(
        configuration
    )

    // Initialization
    var visitorId:String? = null
    init {
        getVisitorId()
    }
    private fun getVisitorId() {
        fpjsClient.getVisitorId { result ->
            Log.d("--FINGERPRINT--",result.toString())
             visitorId = result.visitorId
        }
    }
}