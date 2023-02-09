plugins {
    `java-library`
}

dependencies {
    implementation(project(":analyzer"))
    implementation(project(":config"))
    implementation(project(":gitrawdata"))
    testImplementation("junit:junit:4.13.2")
}

