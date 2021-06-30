plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

apply {
    from("mavenpush.gradle")
}

android {
    compileSdkVersion(ModuleConfig.COMPILE_SDK)
    buildToolsVersion(ModuleConfig.BUILD_TOOLS)

    defaultConfig {
        minSdkVersion(ModuleConfig.MIN_SDK)
        targetSdkVersion(ModuleConfig.TARGET_SDK)
        versionCode = 1
        versionName(ModuleConfig.PROJECT_VERSION)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setConsumerProguardFiles(listOf("consumer-rules.pro"))
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xallow-result-return-type")
        freeCompilerArgs = freeCompilerArgs + listOf("-module-name", "backend2")
    }
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB_JDK7)
    implementation(Dependencies.ANDROID_KTX_CORE)
    implementation(Dependencies.ANDROID_APP_COMPAT)

    // coroutine
    implementation(Dependencies.COROUTINES_CORE)
    implementation(Dependencies.COROUTINES_ANDROID)

    //koin
    implementation(Dependencies.KOIN_CORE)
    implementation(Dependencies.KOIN_CORE_EXT)
    implementation(Dependencies.KOIN_ANDROID)
    //gson
    implementation(Dependencies.GSON)
    //web
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_LOGGIN_INTERCEPTOR)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON)
    //logger
    implementation(Dependencies.LOG_DATA_RECORDER_DATA)
    implementation(Dependencies.LOG_DATA_RECORDER_DOMAIN)
    //test
    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.ANDROID_TEST_JUNIT)
    testImplementation(Dependencies.ANDROID_TEST_ESPRESSO_CORE)
    //Optional -- Robolectric environment
    testImplementation(Dependencies.ANDROID_TEST_CORE)
    testImplementation(Dependencies.ANDROID_ARCH_CORE_TESTING)
    testImplementation(Dependencies.ROBOLECTRIC)
    // kointest
    testImplementation(Dependencies.KOIN_TEST)
    //coroutine
    testImplementation(Dependencies.COROUTINES_TEST)
    //Mock
    testImplementation(Dependencies.MOCKK)
    testImplementation(Dependencies.MOCKK_ANDROID)
    //truth
    testImplementation(Dependencies.TRUTH)
    testImplementation(Dependencies.TRUTH_EXTENSION_JAVA8)
}