package com.edlo.mydemoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

@HiltAndroidApp
class MyDemoApp: Application() {
    companion object {
        lateinit var INSTANCE: MyDemoApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ignoreSSLHandshake()
    }

    fun ignoreSSLHandshake() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkClientTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                override fun checkServerTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
            })
            val sc = SSLContext.getInstance("TLS")
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
        } catch (e: java.lang.Exception) { }
    }
}