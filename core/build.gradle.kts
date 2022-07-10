version = "0.1.0"

plugins {
    id("java-library")
}

repositories {
    mavenLocal()
}

dependencies {
    labyProcessor()
    api(project(":api"))
}

addon {
    internalRelease()
}

tasks.compileJava {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}