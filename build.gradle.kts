// Top-level build file where you can add configuration options  common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    parallel = true
    autoCorrect = false
    disableDefaultRuleSets = false
    buildUponDefaultConfig = true

    jvmTarget = JavaVersion.VERSION_1_8.toString()

    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")

    reports {
        xml.required.set(false)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(false)
        md.required.set(false)
    }

    config.setFrom(files(project.rootDir.resolve("conf/custom-detekt.yml")))
}

dependencies {
    add("detekt", libs.bundles.staticAnalysis.detekt)
    detektPlugins(libs.staticAnalysis.detektCli)
}
