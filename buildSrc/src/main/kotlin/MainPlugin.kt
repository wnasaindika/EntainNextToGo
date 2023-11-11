import org.gradle.api.Plugin
import org.gradle.api.Project

class MainPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        applyPlugin(project)
    }

    private fun applyPlugin(project: Project) {
        project.apply {
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("dagger.hilt.android.plugin")
        }
    }
}