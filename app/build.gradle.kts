import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

fun loadKeyProperties(): Properties {
    val properties = Properties()
    val propertiesFile = rootProject.file("key.properties")
    if (propertiesFile.exists()) {
        properties.load(propertiesFile.inputStream())
    }
    return properties
}

// Load properties
val keyProperties = loadKeyProperties()

android {
    namespace = "com.elhalawany.twittercounterexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.elhalawany.twittercounterexample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String", "API_KEY", "${keyProperties["twitter.api_key"]}")
        buildConfigField("String", "API_SECRET", "${keyProperties["twitter.api_secret"]}")
        buildConfigField("String", "ACCESS_TOKEN", "${keyProperties["twitter.access_token"]}")
        buildConfigField("String", "ACCESS_TOKEN_SECRET", "${keyProperties["twitter.access_token_secret"]}")
        buildConfigField("String", "BEARER_TOKEN", "${keyProperties["twitter.bearer"]}")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.mockito.inline)


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



    implementation(project(":TwitterCounterComponent"))
}