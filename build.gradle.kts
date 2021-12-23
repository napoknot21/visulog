plugins {
    java
    id("org.openjfx.javafxplugin") version "0.0.10"
}

version = "0.0.1"
group = "up"

allprojects {
    repositories {
        mavenCentral()
    }

    plugins.apply("org.openjfx.javafxplugin")
    plugins.apply("java")

    javafx {
        version = "17"
        modules("javafx.controls", "javafx.fxml", "javafx.web")
    }

    java.sourceCompatibility = JavaVersion.VERSION_11

}

