package com.analyticssdk.core

import android.app.Activity
import android.os.Bundle
import com.analyticssdk.core.storage.DriverFactory
// BuildConfig hata verirse Alt+Enter ile import et

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Analytics.init(
            driverFactory = DriverFactory(this),
            url = BuildConfig.SUPABASE_URL,
            apiKey = BuildConfig.SUPABASE_KEY
        )

        Analytics.logEvent(
            eventName = "hayalet_test_basarili",
            parameters = mapOf(
                "durum" to "arayüzsüz çalıştı",
                "hedef" to "staj_cepte"
            )
        )

        println("🚀 Analytics SDK: Uygulama açıldı ve test verisi fırlatıldı!")
    }
}