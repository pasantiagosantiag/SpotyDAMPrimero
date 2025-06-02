plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("java")
}

group = "ies.sequeros.dam.spotydam"
version = "unspecified"

repositories {
    mavenCentral()

}

dependencies {
    //dependencia al otro proyecto
    implementation(project(":lib"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:32.1.1-jre")
    //CSS
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    implementation("fr.brouillard.oss:cssfx:11.5.1")
    //formularios
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0")
    implementation("com.dlsc.formsfx:formsfx:11.6.0")
    //iconos
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-core:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-antdesignicons-pack:12.3.1")

    implementation("org.kordamp.ikonli:ikonli-fontawesome-pack:12.3.1")

    //serializacion json
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")


    //controles extras
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("io.github.palexdev:materialfx:11.17.0")
    implementation("com.jfoenix:jfoenix:9.0.10")

}
javafx {
    version = "22.0.1"
    modules = listOf( "javafx.controls", "javafx.graphics", "javafx.fxml", "javafx.media", "javafx.swing" )
}
application {
    // Define the main class for the application.
    mainClass.set("ies.sequeros.dam.spotydam.Main")
    applicationDefaultJvmArgs = listOf(
        "--add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED"
    )
}
tasks.test {
    useJUnitPlatform()
}