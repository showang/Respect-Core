plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    val publicationsFromMainHost =
        listOf(jvm(), js()).map { it.name } + "kotlinMultiplatform"
    publishing {
        publications {
            matching { it.name in publicationsFromMainHost }.all {
                val targetPublication = this@all
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == targetPublication }
                    .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.kotlin.coroutines)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "me.showang.respect"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility(17)
        targetCompatibility(17)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}


//plugins {
//    id 'com.android.library'
//    id 'kotlin-android'
//    id 'maven-publish'
//}
//group = 'com.github.showang'
//def version_name = '1.1.0'
//
//android {
//    namespace 'me.showang.respect'
//    compileSdk 34
//
//    defaultConfig {
//        minSdkVersion 15
//        versionCode 1100
//        versionName version_name
//
//                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_11
//                targetCompatibility JavaVersion.VERSION_11
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//        }
//    }
//}
//
////dependencies {
////    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines_version"
////
////    testImplementation 'junit:junit:4.13.2'
////}
//
//afterEvaluate {
//    publishing {
//        publications {
//            release(MavenPublication) {
//                from components.release
//                        groupId = "me.showang.showang"
//                artifactId = "respect-core"
//                version = version_name
//            }
//        }
//    }
//}
