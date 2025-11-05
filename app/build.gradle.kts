plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinParcelize )
}

android {
    namespace = "ru.melolchik.vknewsclient"
    compileSdk = 34


    defaultConfig {
        applicationId = "ru.melolchik.vknewsclient"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        addManifestPlaceholders(
            mapOf(
                "VKIDClientID" to "54290700", // ID вашего приложения (app_id).
                "VKIDClientSecret" to "O673uE11P8PRi8iJHpCD", // Ваш защищенный ключ (client_secret).
                "VKIDRedirectHost" to "vk.ru", // Обычно используется vk.ru.
                "VKIDRedirectScheme" to "vk54290700", // Должно быть vk{ID приложения}.
            )
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation("com.google.code.gson:gson:2.10")

//    implementation ("com.vk:android-sdk-core:4.1.0")
//    implementation ("com.vk:android-sdk-api:4.1.0")
    implementation("com.vk.id:vkid:2.6.0")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.vk:android-sdk-core:4.0.1")
    implementation("com.vk:android-sdk-api:4.0.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}