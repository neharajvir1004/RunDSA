plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") //firebase
}

android        {
    namespace = "com.rundsa.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rundsa.app"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    dependencies {
        implementation("androidx.core:core-ktx:1.10.1")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.appcompat:appcompat:1.7.1")
        implementation("com.google.android.material:material:1.13.0")
        implementation("androidx.activity:activity:1.8.2")
        implementation("androidx.constraintlayout:constraintlayout:2.2.1")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("androidx.gridlayout:gridlayout:1.0.0")
        implementation("com.google.firebase:firebase-auth:22.3.0")
        implementation("com.github.kbiakov:CodeView-Android:1.3.2"){
            exclude(group = "com.android.support")
        }
        implementation ("androidx.gridlayout:gridlayout:1.0.0")



        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.3.0")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    implementation("com.google.firebase:firebase-auth:22.3.0") //firebase

    implementation(libs.androidx.core.ktx)
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.google.firebase:firebase-firestore-ktx:24.10.0")//database
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
}