plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
}

version = "0.0.1"
group = "up"

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.web")
}

application {
    mainClassName = "up.visulog.ui.VisulogLauncher"
} //A changer selon la classe App

allprojects {
    repositories {
        mavenCentral()
    }
    plugins.apply("application")
    plugins.apply("org.openjfx.javafxplugin")

    java.sourceCompatibility = JavaVersion.VERSION_1_10

}

dependencies {
    implementation(project(":analyzer"))
    implementation(project(":config"))
    implementation(project(":gitrawdata"))
    testImplementation("junit:junit:4.+")
}

