## Build tools & versions used
  ### The app required folllowing libaries to build the project 

     -  Kotlin version 1.9.10
     -  Gradle version 8.0
     -  Hilt version 2.48
     -  Jetpack Compose  version 1.5.3
     -  Kotlin Coroutine 
     -  Retrofit version 2.9.0
     -  Coil for image cahcing 
     -  Terbine for flow testing 
     -  Matirial 3 for UI design 
     -  ...etc 

## Steps to run the app

     - Git pull or download source code
     - Sync All libaries 
     - Press Run (select real device/emulator)
     - Press filters for sorting 
     - Pull down for fresh data 
     - If errors Restrat WiFi on device    

## What areas of the app did you focus on?

  ### areas of focuse
  - clean code 
  - single data source
  - high cohesion and less coupling in in compose
  - SOLID principle 
  - scalability 
  - maintainibility 

## What was the reason for your focus? What problems were you trying to solve?


  ### problems trying to solve

   - fetching data from network with retrofit
   - timer on list
   - limit 5 items in list
   - filter's with combinations  
   - minimum requrment for the given time  

## How long did you spend on this project?
- 5 -6 hrs finshing the task (UI and Architecture) 
 
## Did you make any trade-offs for this project? What would you have done differently with more time?


## What do you think is the weakest part of your project?

The program is optimized for API levels 26 and higher. Setting up Compse Kotlin with Java 17 can be difficult for beginners; therefore, raising the minimum supported development kit to 26 will simplify the application's configuration. 

## Did you copy any code or dependencies? Please make sure to attribute them here!
 - Matirial 3 Design Guideline
 - Animations from previous projet   
 - I always use this skulton for all my projects for dependencies 

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

## Is there any other information you’d like us to know?

- If the app throwing errors when starting, please restart wifi on your device.
- Have not included end to end test 
