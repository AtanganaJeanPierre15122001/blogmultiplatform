import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.serialization.plugin)
    // alias(libs.plugins.kobwebx.markdown)
}

group = "com.example.blogmultiplatform"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("blogmultiplatform", includeServer = true)

    sourceSets {
//        commonMain.dependencies {
//          // Add shared dependencies between JS and JVM here
//        }
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            // implementation(libs.kobwebx.markdown)
            
        }
        jvmMain.dependencies {
            implementation(libs.kmongo.database)
            implementation(libs.kobwebx.serialization.kotlinx)
            implementation(libs.kotlinx.serialization)
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
        }
    }
}
