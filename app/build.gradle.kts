plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.openclassrooms.tajmahal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.openclassrooms.tajmahal"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        exclude("mockito-extensions/org.mockito.plugins.MockMaker")
    }
}

dependencies {
    implementation("androidx.work:work-runtime:2.8.1")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("com.google.ai.client.generativeai:common:0.3.0")
    implementation("com.google.android.libraries.places:places:3.0.0")
    implementation("androidx.fragment:fragment-testing:1.7.0")

    val hiltVersion = "2.44"
    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    annotationProcessor("com.google.dagger:hilt-compiler:$hiltVersion")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("com.google.android.material:material:1.4.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")

    // Mockito dependencies

    testImplementation("org.mockito:mockito-inline:4.3.1")
    testImplementation("org.mockito:mockito-android:3.12.4")
    testImplementation ("org.mockito:mockito-core:3.12.4")


    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("org.robolectric:robolectric:4.6.1")

    debugImplementation("androidx.fragment:fragment-testing:1.3.6")


    // Hilt testing dependencies
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hiltVersion")
}
