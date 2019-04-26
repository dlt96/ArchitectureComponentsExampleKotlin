plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.example.mvvmkt"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.10")
    implementation("androidx.appcompat:appcompat:1.0.0-beta01")
    implementation("com.google.android.material:material:1.1.0-alpha05")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.0-alpha4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0-alpha4")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1")

    //ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0-alpha02")

    //Room
    implementation("androidx.room:room-runtime:2.1.0-alpha06")
    kapt("androidx.room:room-compiler:2.1.0-alpha06")
    implementation("androidx.room:room-ktx:2.1.0-alpha06")

    //Anko
    implementation("org.jetbrains.anko:anko-common:0.10.5")
}
