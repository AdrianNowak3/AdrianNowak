pluginManagement {
    repositories {
        google() // Repozytorium Google dla wtyczek Androida
        mavenCentral() // Repozytorium Maven
        gradlePluginPortal() // Portal wtyczek Gradle
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApplication"
include(":app")
