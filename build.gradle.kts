import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "9.0.0-beta2"
    id("io.papermc.paperweight.userdev") version "1.7.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    kotlin("plugin.serialization") version "2.1.0"
}

group = "net.crystopia"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.flyte.gg/releases")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.nightexpressdev.com/releases")
    maven("https://jitpack.io")
    maven {
        url = uri("https://maven.pkg.github.com/Crystopia/Econix")
        credentials {
            username = findProperty("USER") as String
            password = findProperty("TOKEN") as String
        }
    }

}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-api:4.17.0")

    implementation("gg.flyte:twilight:1.1.16")

    compileOnly("dev.jorel:commandapi-bukkit-core:9.6.0")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.6.0")
    implementation("dev.jorel:commandapi-bukkit-kotlin:9.6.0")

    // Econix
    compileOnly("me.jesforge:econix:1.2.3")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn(tasks.shadowJar)
    dependsOn(tasks.reobfJar)
}

tasks.shadowJar {
    relocate("net.wesjd.anvilgui", "net.crystopia.lib.anvilgui")
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}

tasks.jar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
}
tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION


paper {
    name = "AffiliateCode"
    version = "1.0"
    description = "Affiliate Plugin to reward player they hire player."
    main = "net.crystopia.affiliatecode.AffiliateCode"
    foliaSupported = false
    apiVersion = "1.19"
    authors = listOf("xyzjesper")
    serverDependencies {
        register("Econix") {
            joinClasspath = true
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}