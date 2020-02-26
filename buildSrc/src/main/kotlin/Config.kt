import Config.Versions.lastAdapterVersion

private const val kotlinVersion = "1.3.61"
private const val androidGradleVersion = "3.5.3"

// Compile dependencies
private const val retrofitVersion = "2.7.1"
private const val okhttpVersion = "3.9.1"
private const val picassoVersion = "2.5.2"

// Unit tests
private const val mockitoVersion = "2.13.0"

//Navigation
private const val nav_version = "2.2.0"

object Config {

    private object Versions {
        // Tools
        const val kotlinVersion = "1.3.61"
        const val androidGradleVersion = "3.5.3"
        const val ktxCoreVersion = "1.1.0"
        const val playServicesVersion = "17.0.0"

        // Android
        const val appcompatVersion = "1.1.0"
        const val materialVersion = "1.2.0-alpha04"
        const val constraintLayoutVersion = "1.1.3"
        const val cardViewVersion = "1.0.0"
        const val swiperefreshlayoutVersion = "1.0.0"
        const val broadcastManagerVersion = "1.0.0"
        const val fragmentVersion = "1.2.0"
        const val lifecycleVersion = "2.2.0"

        // Third-party libs
        const val koinVersion = "2.0.1"
        const val koinArchVersion = "0.9.3"
        const val coroutinesVersion = "1.3.0-RC"
        const val lastAdapterVersion = "2.3.0"

        // Testing libs
        const val junitVersion = "4.12"
        const val androidxJunitVersion = "1.1.1"
        const val espressoCoreVersion = "3.2.0"
        const val mokitoKotlin = "2.2.0"
        const val mokitoInline = "3.0.0"
        const val androidXCoreTestingVersion = "2.0.0"
        const val androidXTestCoreVersion = "1.1.0"
        const val androidXTestRunnerVersion = "1.1.1"
        const val androidXTestRulesVersion = "1.1.1"
    }

    object Tools {
        const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradleVersion}"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
        const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCoreVersion}"
        const val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServicesVersion}"
        const val playServicesMaps = "com.google.android.gms:play-services-maps:${Versions.playServicesVersion}"
        val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
    }

    object Android {
        const val buildToolsVersion = "29.0.2"
        const val minSdkVersion = 17
        const val targetSdkVersion = 29
        const val compileSdkVersion = 29
        const val applicationId = "com.currentweather"
        const val versionCode = 1
        const val versionName = "1.0"

        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
        const val material = "com.google.android.material:material:${Versions.materialVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
        const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"
        const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayoutVersion}"
        const val fragmentKtx= "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
        const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
        val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
        val navigation_ui = "androidx.navigation:navigation-ui-ktx:$nav_version"
    }

    object ThirdPartyLibs {
        const val koinCore= "org.koin:koin-core:${Versions.koinVersion}"
        const val koinScope= "org.koin:koin-androidx-scope:${Versions.koinVersion}"
        const val koinViewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
        const val koinAndroid = "org.koin:koin-android:${Versions.koinVersion}"
        const val koinArch = "org.koin:koin-android-architecture:${Versions.koinArchVersion}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
        val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        val okhttp_interceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        val retrofit_gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        val picasso = "com.squareup.picasso:picasso:$picassoVersion"
        val lastAdapter = "com.github.nitrico.lastadapter:lastadapter:$lastAdapterVersion"
    }

    object TestingLibs {
        const val junit = "junit:junit:${Versions.junitVersion}"
        const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunitVersion}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
        const val koinTest = "org.koin:koin-test:${Versions.koinVersion}"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mokitoKotlin}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mokitoInline}"
        const val androidXCoreTesting = "androidx.arch.core:core-testing:${Versions.androidXCoreTestingVersion}"
        const val androidXTestCore = "androidx.test:core:${Versions.androidXTestCoreVersion}"
        const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTestRunnerVersion}"
        const val androidXTestRules = "androidx.test:rules:${Versions.androidXTestRulesVersion}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
    }
}