package com.analyticssdk.core

import android.app.Activity
import android.os.Bundle
import com.analyticssdk.core.storage.DriverFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
                "hedef" to "loglar yazdırılıyor"
            )
        )

        CoroutineScope(Dispatchers.Default).launch {
            Analytics.logScreenEnter("Ana_Ekran")
            delay(3000)
            Analytics.logScreenExit("Ana_Ekran")
        }

        println("🚀 Analytics SDK: Uygulama açıldı ve test verisi fırlatıldı!")
    }
}