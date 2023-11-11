object Dependencies {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttpMockServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val composeBomb = "androidx.compose:compose-bom:${Versions.composeBomb}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeMatirial3 = "androidx.compose.material3:material3"
    const val composeMatirial = "androidx.compose.material:material:${Versions.composeMaterial}"
    const val composeActivity = "androidx.activity:activity-compose"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling"
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4"
    const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest"

    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroidTest = "io.mockk:mockk-android:${Versions.mockk}"

    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"

    const val googleMatirial = "com.google.android.material:material:${Versions.googleMaterial}"

    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val androidJunitExt = "androidx.test.ext:junit:${Versions.jUnitExt}"

    const val coreExpresso = "androidx.test.espresso:espresso-core:${Versions.expresso}"

    const val kotlinExt = "androidx.core:core-ktx:${Versions.ktx}"

    const val appCompact = "androidx.appcompat:appcompat:${Versions.appCompact}"

    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose: ${Versions.viewModelCompose}"
}