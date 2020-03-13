plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Config.Android.compileSdkVersion)
    buildToolsVersion(Config.Android.buildToolsVersion)

    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Android.minSdkVersion)
        targetSdkVersion(Config.Android.targetSdkVersion)
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    dataBinding.isEnabled = true

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Config.Tools.kotlinStd)
    implementation(Config.Tools.ktxCore)
    implementation(Config.Tools.playServicesLocation)

    implementation(Config.Android.appcompat)
    implementation(Config.Android.material)
    implementation(Config.Android.constraintLayout)
    implementation(Config.Android.cardView)
    implementation(Config.Android.swiperefreshlayout)
    implementation(Config.Android.navigation_fragment)
    implementation(Config.Android.navigation_ui)
    implementation(Config.Android.googleMap)
    implementation(Config.Android.workManager)
    implementation(Config.Android.room)
    implementation(Config.Android.roomKtx)

    implementation(Config.ThirdPartyLibs.koinCore)
    implementation(Config.ThirdPartyLibs.koinScope)
    implementation(Config.ThirdPartyLibs.koinViewmodel)
    implementation(Config.ThirdPartyLibs.koinAndroid)
    implementation(Config.ThirdPartyLibs.koinArch)

    implementation(Config.ThirdPartyLibs.coroutinesAndroid)
    implementation(Config.ThirdPartyLibs.coroutinesCore)

    implementation(Config.ThirdPartyLibs.okhttp)
    implementation(Config.ThirdPartyLibs.okhttp_interceptor)
    implementation(Config.ThirdPartyLibs.retrofit)
    implementation(Config.ThirdPartyLibs.retrofit_gson)
    implementation(Config.ThirdPartyLibs.lastAdapter)

    implementation(Config.ThirdPartyLibs.picasso)

    kapt(Config.AnnotationProcessors.room)

    testImplementation(Config.TestingLibs.junit)
    testImplementation(Config.TestingLibs.koinTest)
    testImplementation(Config.TestingLibs.mockitoKotlin)
    testImplementation(Config.TestingLibs.mockitoInline)
    testImplementation(Config.TestingLibs.androidXCoreTesting)
    testImplementation(Config.TestingLibs.coroutinesTest)
    testImplementation(Config.TestingLibs.roomTest)

    androidTestImplementation(Config.TestingLibs.androidxJunit)
    androidTestImplementation(Config.TestingLibs.espressoCore)
    androidTestImplementation(Config.TestingLibs.androidXTestCore)
    androidTestImplementation(Config.TestingLibs.androidXTestRunner)
    androidTestImplementation(Config.TestingLibs.androidXTestRules)
}
