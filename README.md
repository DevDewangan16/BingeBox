# 🎬 BingeBox - Movie & TV Show Discovery App

A **modern Android application** built with **Kotlin and Jetpack Compose** that helps users discover and explore popular **movies** and **TV shows** using the [Watchmode API](https://api.watchmode.com/).

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg)
![Material 3](https://img.shields.io/badge/Design-Material%203-purple.svg)


## ✨ Features

### 🎯 Core Functionality

* 📺 **Dual Content Types:** Browse Movies & TV Shows with seamless tab navigation
* 🖼️ **Rich Media Display:** High-quality posters with fallback handling
* ⭐ **Ratings:** Visual star indicators for quick rating overview
* 📖 **Details View:** Full information including plot, release date & ratings
* 🔄 **State Persistence:** Remembers active tab when navigating back

### 🎨 UI/UX Highlights

* 🌙 **Dark Theme**
* ✨ **Shimmer Loading** animations for smooth experience
* 📱 **Responsive Grid Layout** optimized for various screen sizes
* 🎭 **Featured Section** with gradient overlay hero banners
* 💫 **Animations** & transitions following **Material 3** principles

### ⚙️ Technical Features

* 🏗️ **MVVM Architecture** with clear separation of concerns
* 🌐 **Retrofit + RxJava** for efficient, reactive API calls
* 🖼️ **Coil** for async image loading and caching
* 💾 **State Management:** Kotlin Flows for reactive updates
* 🔒 **Secure API Key Management** via BuildConfig/local.properties
* 🔄 **Error Handling** with retry options
* 📊 **HTTP Logging Interceptor** for debugging

---

## 🛠️ Tech Stack

| Category                 | Technologies                           |
| ------------------------ | -------------------------------------- |
| **Language**             | Kotlin                                 |
| **UI Framework**         | Jetpack Compose                        |
| **Design System**        | Material Design 3                      |
| **Architecture**         | MVVM + Repository Pattern              |
| **Networking**           | Retrofit, OkHttp, Gson Converter       |
| **Reactive Programming** | RxJava3, RxAndroid                     |
| **Image Loading**        | Coil                                   |
| **Navigation**           | Navigation Compose                     |
| **State Management**     | Kotlin Flows, ViewModel                |

---

## 🧱 Project Structure

```
com.example.movieapp/
├── data/
│   ├── model/
│   ├── network/
│   └── repository/
├── ui/
│   ├── home/
│   ├── details/
│   └── theme/
├── util/
└── MainActivity.kt
```

**Highlights**

* `data/` → API integration & data models
* `ui/` → Screens & theme configuration
* `util/` → Constants & shimmer effects

---


## 🧩 Development Insights

### ✅ What Worked Well

* Jetpack Compose simplified UI building
* Material 3 enhanced design consistency
* RxJava enabled smooth async handling
* MVVM ensured modular, testable code

### ⚡ Challenges & Solutions

| Challenge           | Solution                                  | Key Learning                  |
| ------------------- | ----------------------------------------- | ----------------------------- |
| API Rate Limiting   | Limited API calls, used fallback handling | Implement rate-limiting logic |
| Missing Poster URLs | Fetched additional detail calls           | Validate API data early       |
| State Reset on Back | Used SavedStateHandle                     | Preserve state in navigation  |
| Shimmer Mismatch    | Custom shimmer per layout                 | Match loading states visually |
| API Key Exposure    | Used BuildConfig/local.properties         | Never hardcode secrets        |

---

## 📋 Assumptions

| Type       | Key Assumptions                                    |
| ---------- | -------------------------------------------------- |
| **API**    | Stable response structure, consistent data         |
| **Design** | Mobile-first portrait orientation                  |
| **User**   | Familiar with streaming platforms                  |
| **Tech**   | Android 7.0+ (SDK 24+), active internet connection |

---

## 🐞 Known Issues

* ⏱️ Slight delay during initial load (due to multiple API calls)
* 💾 High memory usage with large image sets
* ❌ No offline mode yet
* 📺 Tablet/landscape UI not fully optimized

---

## 🔗 API Reference

| Endpoint               | Purpose              | Params                                |
| ---------------------- | -------------------- | ------------------------------------- |
| `/list-titles/`        | Fetch popular titles | `apiKey`, `types`, `limit`, `sort_by` |
| `/title/{id}/details/` | Fetch detailed info  | `apiKey`, `id`                        |

---

