import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.get

fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandler.implementation(dependency: Dependency) {
    add("implementation", dependency)
}

fun DependencyHandler.test(dependency: String) {
    add("test", dependency)
}

fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandler.androidTest(dependency: String) {
    add("androidTest", dependency)
}

fun DependencyHandler.annotationProcessor(dependency: String) {
    add("annotationProcessor", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: String) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: Dependency) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.kaptAndroidTest(dependency: String) {
    add("kaptAndroidTest", dependency)
}

fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.kapt(dependency: String) {
    add("kapt", dependency)
}

fun DependencyHandler.room() {
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)
    annotationProcessor(Dependencies.roomCompiler)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterMoshi)
    implementation(Dependencies.retrofitConverterGson)
}

fun DependencyHandler.okhttp() {
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
    testImplementation(Dependencies.okhttpMockServer)
}

fun DependencyHandler.moshi() {
    implementation(Dependencies.moshi)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    androidTestImplementation(Dependencies.hiltAndroidTesting)
    kaptAndroidTest(Dependencies.hiltAndroidCompiler)
    implementation(Dependencies.hiltNavigation)
}

fun DependencyHandler.compose() {
    val composeBom = platform(Dependencies.composeBomb)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMatirial3)
    implementation(Dependencies.composeMatirial)
    implementation(Dependencies.googleMatirial)
    implementation(Dependencies.matirail3WindowSize)
    implementation(Dependencies.composeToolingPreview)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeUiTestManifest)
    androidTestImplementation(Dependencies.composeUiTest)
}

fun DependencyHandler.mockk() {
    testImplementation(Dependencies.mockk)
    testImplementation(Dependencies.mockkAndroidTest)
}

fun DependencyHandler.turbine() {
    testImplementation(Dependencies.turbine)
}

fun DependencyHandler.coil() {
    implementation(Dependencies.coil)
}

fun DependencyHandler.coroutinesTest() {
    testImplementation(Dependencies.coroutinesTest)
}

fun DependencyHandler.jUnit() {
    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.androidJunitExt)
}

fun DependencyHandler.expresso() {
    androidTestImplementation(Dependencies.coreExpresso)
}

fun DependencyHandler.ktx() {
    implementation(Dependencies.kotlinExt)
}

fun DependencyHandler.viewModelCompose() {
    implementation(Dependencies.viewModelCompose)
}

fun DependencyHandler.appCompact() {
    implementation(Dependencies.appCompact)
}

fun DependencyHandler.getAndroid() {
    this.extensions.getByName("android")
}