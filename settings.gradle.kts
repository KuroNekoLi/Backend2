pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "http://192.168.99.70:8081/repository/maven-public/") {
            isAllowInsecureProtocol = true
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "http://192.168.99.70:8081/repository/maven-public/") {
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "Backend2"
include(":backend2")
include(":app")
