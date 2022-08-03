import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":utils"))

    // Kotlin
    implementation(Libs.kotlin)

    // Coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)

    // Lifecycle
    implementation(Libs.lifecycleRuntimeKtx)

    // Okhttp
    implementation(platform(Libs.okhttpBom))
    implementation(Libs.okhttp)

    // Hilt
    kapt(Libs.daggerHiltCompiler)
    implementation(Libs.hilt)

    // Ktx
    implementation(Libs.coreKtx)
    implementation(Libs.lifecycleViewModel)

    // Misc
    implementation(Libs.timber)
}