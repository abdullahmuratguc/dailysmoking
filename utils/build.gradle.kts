plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Ktx
    implementation(Libs.activityKtx)
    implementation(Libs.fragmentKtx)

    // Epoxy
    implementation(Libs.appCompat)

    // RecyclerView
    implementation(Libs.recyclerView)

    // Hilt
    kapt(Libs.daggerHiltCompiler)
    implementation(Libs.hilt)

    // Lifecycle
    implementation(Libs.lifecycleCommon)
    implementation(Libs.lifecycleRuntime)

    // Misc
    implementation(Libs.timber)

    // Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.okhttp)

    // Glide
    implementation(Libs.glide)
    kapt(Libs.glideAnnotationProcessor)

    // Kotlin Serialization
    implementation(Libs.kotlinSerialization)

    // Coroutines
    implementation(Libs.coroutinesCore)
}