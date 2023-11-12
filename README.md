## Next To Go Race using Jetpack compose + Clean Architecture + MVVM

### initial design
![initial_design](https://github.com/wnasaindika/EntainNextToGo/assets/1547028/517aab3b-dd69-4585-ac5c-b3824b2c90d4)

### final app (video)
https://github.com/wnasaindika/EntainNextToGo/assets/1547028/d4f6789b-1c46-4435-bbbc-644309afb13c

## Build tools & versions used

### The app required following libraries to build the project

- Kotlin version 1.9.10
- Gradle version 8.0
- Hilt version 2.48
- Jetpack Compose version 1.5.3
- Kotlin Coroutine
- Retrofit version 2.9.0
- Coil for image caching
- Turbine for flow testing
- Material 3 for UI design

### buildSrc

If the application requires modularization, use central points to govern the dependencies.

```
    compose()
    retrofit()
    okhttp()
    moshi()
    hilt()
    room()
    turbine()
    mockk()
    coil()
    coroutinesTest()
    jUnit()
    ktx()
    appCompact()
    viewModelCompose()
    expresso()
```

## Steps to run the app

- Git pull or download source code
- Sync All libaries
- Press Run (select real device/emulator)

## What areas of the app did you focus on?

### areas of focus
- clean architecture
- app theming
- MVVM architecture
- central repository (Obtain data from the network and cache it; the cache will be updated based on user events
  or if all races have expired if the race count is less than 5.)
- SOLID principle
- scalability
- maintainability

### problems trying to solve

- fetching data from network with retrofit and providing single data source
- timer on the racing list
- limit network request when race event expired
- list refreshed
- limit race event with filters
- full fill the given requirements

## How long did you spend on this project?

- 5 - 6 hrs finishing the task (UI and Architecture)
- 2 - 3 hrs for bug fixing/optimization and documentation

## The weakest part of your project

- minimum size for any race is 5, if the remote api return less then 5 items apps will throw an
  error (potential race condition if the server always return less than 5 element for any request)
- user not allowed to refresh if app status is error
- If the minimum number of races is five and the race begins in a few days, the user must pull to
  refresh the app.

## dependencies list

- Matirial 3 Design with Jetpack Compse
- Android dependencies and project listed below

```
    val composeBom = platform("androidx.compose:compose-bom:2023.09.02")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material:1.6.0-alpha08")
    implementation("androidx.activity:activity-compose")
    //retrofit network call
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")


    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")


    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // Integration with activities
    implementation("androidx.activity:activity-compose:1.8.0")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")


    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //image caching
    implementation("io.coil-kt:coil-compose:2.5.0")


    //hilt testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")


    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // kotlin mockk for mocking
    testImplementation("io.mockk:mockk:1.13.8")
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    // coroutine for test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    //turbine for flow test
    testImplementation("app.cash.turbine:turbine:1.0.0")

    //retorfit test
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.11")
```

## Running test

- right click androidTest folder -> press Run All test
- right click test folder -> press Run All test

## Troubleshooting

- If the app throwing errors when launching, please restart wifi on your device.

