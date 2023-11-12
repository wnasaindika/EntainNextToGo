import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class MainPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        applyPlugin(project)
        applyProjectConfiguration(project)
    }

    private fun applyPlugin(project: Project) {
        project.apply {
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("dagger.hilt.android.plugin")
        }
    }

    private fun applyProjectConfiguration(project: Project) {

        project.android().apply {
            compileSdk = ProjectConfig.compileSdk

            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
                consumerProguardFiles("consumer-rules.pro")
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
    }

    private fun Project.android() = extensions.getByType(LibraryExtension::class.java)

}