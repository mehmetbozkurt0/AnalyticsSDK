# Analytics SDK (Kotlin Multiplatform)
A lightweight, robust, and offline-first Analytics SDK built with Kotlin Multiplatform (KMP). Designed to seamlessly track user events and screen durations across Android and Desktop (JVM) applications with built-in network resilience.
<br> </br>
## ✨ Features
* **Offline-First Architecture:** Utilizes SQLDelight and SQLite to safely store events locally when the device is offline.

* **Auto-Synchronization:** Automatically batches and syncs local data to your backend (e.g., Supabase) via Ktor once the network is available.

* **Automatic Device Context:** Automatically injects device information (OS name, OS version, device model, and manufacturer) into every event payload.

* **Screen Duration Tracking:** Easily track how long a user stays on a specific screen with auto-calculated duration metrics.

* **KMP Ready:** Share the exact same analytics logic across Android and JVM (Desktop) targets.
<br> </br>
## 📦 Installation
### Step 1. Add the JitPack repository
In your root settings.gradle.kts or project level build.gradle.kts:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```
### Step 2. Add the dependency
Add the following to your App module's build.gradle.kts:

```kotlin
dependencies {
    implementation("com.github.mehmetbozkurt0:analyticssdk:1.0.1")
}
```
<br> </br>
## 💡 Backend Compatibility Note
While the current version is optimized for **Supabase** (using specific headers for `apikey` and `Authorization`), this SDK is designed to be flexible. You can use it with any backend service that can receive a standard HTTP POST request with the following JSON payload:

```json
{
  "id": "event_id",
  "event_name": "event_name",
  "timestamp": 123456789,
  "parameters": { "key": "value" }
}
```

If your backend requires different authentication headers or a different JSON schema, the current NetworkManager can be easily adapted or extended in future versions.

<br> </br>
## 🚀 Usage
Initialize the SDK once at the entry point of your application.

```kotlin
import com.analyticssdk.core.Analytics
import com.analyticssdk.core.storage.DriverFactory

Analytics.init(
    driverFactory = DriverFactory(context), 
    url = "https://YOUR_PROJECT_ID.supabase.co/rest/v1/events",
    apiKey = "YOUR_API_KEY"
)
```


Track specific user actions. Device info and timestamps are appended automatically.

```kotlin
Analytics.logEvent(
    eventName = "purchase_completed",
    parameters = mapOf(
        "item_id" to "12345",
        "price" to "19.99"
    )
)
```


The SDK automatically calculates the duration and fires a screen_view event upon exit.
```kotlin
// When the user enters:
Analytics.logScreenEnter("Home_Screen")

// When the user leaves:
Analytics.logScreenExit("Home_Screen")
```
<br> </br>
## 🛠 Tech Stack
* Kotlin Multiplatform

* Ktor (Networking)

* SQLDelight (Local Database)

* Coroutines (Asynchronous Programming)
