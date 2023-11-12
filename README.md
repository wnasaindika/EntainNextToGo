## Next To Go Racing + Jetpack Compose + Clean Architecture + MVVM

### initial design
![initial_design](https://github.com/wnasaindika/EntainNextToGo/assets/1547028/8f6b6ba9-3d2e-41ff-9346-9fb25d023d4b)


### final app (video)


https://github.com/wnasaindika/EntainNextToGo/assets/1547028/e79a081d-e061-487a-a2b4-05f57e4d567a



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
- User must select all filter to show all races (Just a modification requirement #4 )

## Running test

- right click androidTest folder -> press Run All test
- right click test folder -> press Run All test

## Troubleshooting

- If the app throwing errors when launching, please restart wifi on your device.

