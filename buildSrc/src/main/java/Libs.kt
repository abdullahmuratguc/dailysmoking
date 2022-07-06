
/**
 * Created by Murat
 */
object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"

    const val okhttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor"

    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

    const val coreKtx = "androidx.core:core-ktx:" + Versions.coreKtx
    const val appCompat = "androidx.appcompat:appcompat:" + Versions.appCompat
    const val material = "com.google.android.material:material:" + Versions.materialVersion
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:" + Versions.constraintVersion
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:" + Versions.lifecycleVersion
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Versions.lifecycleVersion
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:" + Versions.lifecycleVersion
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:" + Versions.navigationVersion
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:" + Versions.navigationVersion
    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"

    const val firebaseBoom = "com.google.firebase:firebase-bom:${Versions.firebaseBoom}"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics"

    const val pieChart = "com.github.PhilJay:MPAndroidChart:"+ Versions.pieChartVersion

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotationProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val roomRuntime = "androidx.room:room-runtime:" + Versions.roomVersion
    const val roomCompiler = "androidx.room:room-compiler:" + Versions.roomVersion
    const val roomKtx = "androidx.room:room-ktx:" + Versions.roomVersion

    const val hilt = "com.google.dagger:hilt-android:" + Versions.hiltVersion
    const val hiltCompiler =  "com.google.dagger:hilt-compiler:" + Versions.hiltVersion
    const val hiltViewModel =  "androidx.hilt:hilt-lifecycle-viewmodel:" + Versions.hiltViewModelVersion
    const val hiltAndroidCompiler =  "androidx.hilt:hilt-compiler:" + Versions.hiltAndroidCompilerVersion
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:2.40.5"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:" + Versions.coroutinesVersion
    const val coroutinesAndroid =  "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + Versions.coroutinesVersion

    // Coroutine Lifecycle Scopes

    const val jUnit = "junit:junit:" + Versions.jUnitVersion
    const val jUnitExt = "androidx.test.ext:junit:" + Versions.jUnitExtVersion
    const val espresso = "androidx.test.espresso:espresso-core:" + Versions.espressoVersion
}