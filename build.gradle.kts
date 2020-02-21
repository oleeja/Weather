// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath(Config.Tools.androidGradle)
        classpath(Config.Tools.kotlinGradlePlugin)
        classpath(Config.Tools.navigationSafeArgs)
    }
}
allprojects {
    repositories {
        jcenter()
        google()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}