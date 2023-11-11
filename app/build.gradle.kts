plugins {
    id("com.android.application")
}

apply<MainPlugin>()

android {
    namespace = "com.entain.next"

    compileSdk = ProjectConfig.compileSdk
    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName
        testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.composeVersion
    }

    buildFeatures {
        compose = true
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
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
}