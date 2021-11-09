plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = ModuleConfig.COMPILE_SDK
    buildToolsVersion = ModuleConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        applicationId = "com.cmoney.backend.sample"
        minSdk = ModuleConfig.MIN_SDK
        targetSdk = ModuleConfig.TARGET_SDK
        versionCode = 1
        versionName = ModuleConfig.PROJECT_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROID_KTX_CORE)
    implementation(Dependencies.ANDROID_APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)
    implementation(Dependencies.ANDROID_CONSTRAINT_LAYOUT)
    implementation(Dependencies.ANDROID_LIFECYCLE_LIVE_DATA_KTX)
    implementation(Dependencies.ANDROID_LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.ANDROID_LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependencies.KOIN_ANDROID_EXT)
    implementation(Dependencies.KOIN_ANDROIDX_VIEWMODEL)
    implementation(Dependencies.GSON)
    implementation(Dependencies.COROUTINES_ANDROID)

    implementation(project(":backend2"))
    implementation(Dependencies.LOG_DATA_RECORDER_DATA)
    implementation(Dependencies.LOG_DATA_RECORDER_DOMAIN)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROID_TEST_JUNIT)
    androidTestImplementation(Dependencies.ANDROID_TEST_ESPRESSO_CORE)
}