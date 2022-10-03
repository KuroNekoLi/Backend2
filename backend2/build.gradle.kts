plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlinx.kover")
}

apply {
    from("mavenpush.gradle")
}

android {
    compileSdk = ModuleConfig.COMPILE_SDK
    buildToolsVersion = ModuleConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = ModuleConfig.MIN_SDK
        targetSdk = ModuleConfig.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + listOf("-module-name", "backend2")
    }
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROID_KTX_CORE)
    implementation(Dependencies.ANDROID_APPCOMPAT)
    // coroutine
    implementation(Dependencies.COROUTINES_CORE)
    implementation(Dependencies.COROUTINES_ANDROID)
    //koin
    implementation(Dependencies.KOIN_ANDROID)
    //gson
    implementation(Dependencies.GSON)
    //web
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_URL_CONNECTION)
    implementation(Dependencies.OKHTTP_LOGGIN_INTERCEPTOR)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON)
    //logger
    implementation(Dependencies.LOG_DATA_RECORDER_DATA)
    implementation(Dependencies.LOG_DATA_RECORDER_DOMAIN)
    // utils
    implementation(Dependencies.CMONEY_UTILS_ANDROID)
    //test
    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.ANDROID_TEST_JUNIT)
    testImplementation(Dependencies.ANDROID_TEST_ESPRESSO_CORE)
    //Optional -- Robolectric environment
    testImplementation(Dependencies.ANDROID_TEST_CORE)
    testImplementation(Dependencies.ANDROID_TEST_ARCH_CORE_TESTING)
    testImplementation(Dependencies.ROBOLECTRIC)
    // kointest
    testImplementation(Dependencies.KOIN_TEST)
    //coroutine
    testImplementation(Dependencies.COROUTINES_TEST)
    // okhttp
    testImplementation(Dependencies.OKHTTP_MOCKWEBSERVER)
    //Mock
    testImplementation(Dependencies.MOCKK)
    testImplementation(Dependencies.MOCKK_ANDROID)
    //truth
    testImplementation(Dependencies.TRUTH)
    testImplementation(Dependencies.TRUTH_EXTENSION_JAVA8)
    // utils-test
    testImplementation(Dependencies.CMONEY_UTILS_TEST)
}