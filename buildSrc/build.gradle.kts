import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.library:com.android.library.gradle.plugin:8.1.2")
    implementation("com.android.application:com.android.application.gradle.plugin:8.1.2")
    implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.9.10")
    implementation("com.google.dagger.hilt.android:com.google.dagger.hilt.android.gradle.plugin:2.48")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
}
