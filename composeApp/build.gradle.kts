plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.sqldelight)
    kotlin("plugin.serialization") version "1.9.22"
}

kotlin {
    // Sorun çıkaran compilerOptions kısmını sildik, sadece metodun kendisi kaldı
    androidTarget()

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.android)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.sqldelight.runtime)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(libs.sqldelight.jvm)
        }
    }
}

android {
    namespace = "com.analyticssdk.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.analyticssdk.core"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    databases {
        create("AnalyticsDatabase"){
            packageName.set("com.analyticssdk.core.db")
        }
    }
}