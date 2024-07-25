plugins {
    id("java")
}

group = "tech.paramount.cmsdq"
version = "1.0-SNAPSHOT"

val mtviSnapshotUrl = uri("https://nexus.mtvi.com/nexus/content/repositories/snapshots")
val mtviReleaseUrl = uri("https://nexus.mtvi.com/nexus/content/repositories/releases")
val mtviAtlassianUrl = uri("https://nexus.mtvi.com/nexus/content/repositories/atlassian")

allprojects {
    group = "tech.paramount.cmsdq"

    val mtviPullHost by extra("nexus.mtvi.com:18081")
    val mtviPushHost by extra("nexus.mtvi.com:18079")
    val mtviUser = project.properties["mtviNexusUsername"] as String
    val mtviPassword = project.properties["mtviNexusPassword"] as String

    tasks.register<Exec>("dockerLoginPushHub") {
        commandLine("docker", "login", "-u", mtviUser, "-p", mtviPassword, mtviPushHost)
    }
    tasks.register<Exec>("dockerLoginPullHub") {
        commandLine("docker", "login", "-u", mtviUser, "-p", mtviPassword, mtviPullHost)
    }
    tasks.register<Exec>("dockerLogoutPushHub") {
        commandLine("docker", "logout", mtviPushHost)
    }
    tasks.register<Exec>("dockerLogoutPullHub") {
        commandLine("docker", "logout", mtviPullHost)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = mtviReleaseUrl
            credentials {
                username = project.properties["mtviNexusUsername"] as String
                password = project.properties["mtviNexusPassword"] as String
            }
        }
        maven {
            url = mtviSnapshotUrl
            credentials {
                username = project.properties["mtviNexusUsername"] as String
                password = project.properties["mtviNexusPassword"] as String
            }
            metadataSources {
                mavenPom()
                artifact()
            }
        }
        maven {
            url = mtviAtlassianUrl
            credentials {
                username = project.properties["mtviNexusUsername"] as String
                password = project.properties["mtviNexusPassword"] as String
            }
        }
        maven {
            url = uri("https://nexus.mtvi.com/nexus/content/groups/public")
            credentials {
                username = project.properties["mtviNexusUsername"] as String
                password = project.properties["mtviNexusPassword"] as String
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    wrapper {
        gradleVersion = project.properties["gradle.version"] as String
        distributionType = Wrapper.DistributionType.ALL
    }
    jar {
        enabled = false
    }
}
