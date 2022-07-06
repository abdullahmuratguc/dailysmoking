// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
        classpath(kotlin("android-extensions", version = Versions.kotlin))
        classpath(kotlin("serialization", version = Versions.kotlin))
        classpath(Libs.hiltGradlePlugin)
        classpath(Libs.navigationSafeArgs)
        classpath(Libs.crashlytics)
        classpath(Libs.googleServices)
    }
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        tasks.withType(KotlinCompile::class).all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
                allWarningsAsErrors = false
                freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
            }
        }

        // BaseExtension is common parent for application, library and test modules
        extensions.findByType<BaseExtension>() ?: return@afterEvaluate

        configure<BaseExtension> {
            defaultConfig {
                compileSdkVersion(Config.compileSdk)
                minSdkVersion(Config.minSdk)
                targetSdkVersion(Config.targetSdk)

                versionName = Config.versionName
                versionCode = Config.versionCode
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            testOptions {
                unitTests.apply {
                    isIncludeAndroidResources = true
                    isReturnDefaultValues = true
                }
            }
        }
    }
}