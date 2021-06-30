plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(ModuleConfig.COMPILE_SDK)
    buildToolsVersion(ModuleConfig.BUILD_TOOLS)

    defaultConfig {
        applicationId = "com.cmoney.backend.sample"
        minSdkVersion(ModuleConfig.MIN_SDK)
        targetSdkVersion(ModuleConfig.TARGET_SDK)
        versionCode = 1
        versionName = ModuleConfig.PROJECT_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB_JDK7)
    implementation(Dependencies.ANDROID_KTX_CORE)
    implementation(Dependencies.ANDROID_APP_COMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)
    implementation(Dependencies.ANDROID_CONSTRAINT_LAYOUT)
    implementation(Dependencies.ANDROID_LIFECYCLE_LIVE_DATA_KTX)
    implementation(Dependencies.ANDROID_LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.ANDROID_LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependencies.KOIN_ANDROIDX_VIEW_MODEL)
    implementation(Dependencies.GSON)
    implementation(Dependencies.COROUTINES_ANDROID)

    implementation(project(":backend2"))
    implementation(Dependencies.LOG_DATA_RECORDER_DATA)
    implementation(Dependencies.LOG_DATA_RECORDER_DOMAIN)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROID_TEST_JUNIT)
    androidTestImplementation(Dependencies.ANDROID_TEST_ESPRESSO_CORE)
}