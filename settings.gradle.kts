pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // Add the lines below
        maven {
            url = uri("https://jitpack.io")
        }
        maven { url =uri("https://maven.fpregistry.io/releases") }
    }
}

rootProject.name = "FingerPrint Kotlin"
include(":app")
