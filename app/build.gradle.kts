plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
}

android {
    defaultConfig {
        applicationId = Config.applicationId
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }
}

dependencies {
    implementation (project(":timerx-core"))
    implementation (project(":core"))
    implementation (project(":utils"))

    implementation (Libs.coreKtx)
    implementation (Libs.appCompat)
    implementation (Libs.material)
    implementation (Libs.constraintLayout)
    implementation (Libs.lifecycleLivedata)
    implementation (Libs.lifecycleViewModel)
    implementation (Libs.lifecycleRuntimeKtx)
    implementation (Libs.navigationFragment)
    implementation (Libs.navigationUi)
    implementation (Libs.pieChart)
    implementation (Libs.timber)

    implementation (Libs.roomRuntime)
    kapt (Libs.roomCompiler)
    implementation (Libs.roomKtx)

    implementation (Libs.hilt)
    kapt (Libs.hiltCompiler)
    implementation (Libs.hiltViewModel)
    kapt (Libs.hiltAndroidCompiler)

    // Coroutines
    implementation (Libs.coroutinesCore)
    implementation (Libs.coroutinesAndroid)

    implementation (platform(Libs.firebaseBoom))
    implementation (Libs.firebaseCrashlytics)
    implementation(Libs.gsonConverter)

    testImplementation (Libs.jUnit)
    androidTestImplementation (Libs.jUnitExt)
    androidTestImplementation (Libs.espresso)
}