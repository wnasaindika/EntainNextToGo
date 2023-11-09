## Build tools & versions used
  ### The app required following libraries to build the project 

 -  Kotlin version 1.9.10
 -  Gradle version 8.0
 -  Hilt version 2.48
 -  Jetpack Compose  version 1.5.3
 -  Kotlin Coroutine 
 -  Retrofit version 2.9.0
 -  Coil for image cahcing 
 -  Terbine for flow testing 
 -  Matirial 3 for UI design

## Steps to run the app

 - Git pull or download source code
 - Sync All libaries 
 - Press Run (select real device/emulator)

## What areas of the app did you focus on?
  ### design for requirements  
  ### areas of focus
- clean architecture 
- app theming 
- MVVM architecture
- reduce network request with caching
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
 - 5 - 6 hrs finshing the task (UI and Architecture) 
 - 2 - 3 hrs for bug fixing/optimization and doucmentation 

## The weakest part of your project
 - minimum size for any race is 5, if the remote api return less then 5 items apps will throw an error (this avoid race condition if the server return less than 5 element for any count)
 
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

