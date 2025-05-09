import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildConfig)
}

kotlin {
    jvm()

    sourceSets {

        commonMain.dependencies {
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)

            api(libs.slf4j.simple)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.postgres)
            implementation(libs.supabase.auth)
            implementation(libs.ktor.client.core)
            implementation(libs.supabase.storage)
            implementation(libs.ktor.client.cio)
            implementation(compose.materialIconsExtended)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.kotlinx.datetime)
            implementation(libs.composeIcons.featherIcons)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }

    }
}

compose.desktop {
    application {
        mainClass = "org.radlance.matuledesktop.core.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MatuleDesktop"
            packageVersion = "1.0.0"
        }
    }
}

buildConfig {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    val supabaseUrl = properties.getProperty("supabase.url")
    buildConfigField("String", "SUPABASE_URL", supabaseUrl)

    val supabaseKey = properties.getProperty("supabase.key")
    buildConfigField("String", "SUPABASE_KEY", supabaseKey)
}
