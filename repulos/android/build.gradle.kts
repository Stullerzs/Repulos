allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val newBuildDir: Directory =
    rootProject.layout.buildDirectory
        .dir("../../build")
        .get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    val newSubprojectBuildDir: Directory = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)

    afterEvaluate {
        val androidExt = extensions.findByName("android")
        if (androidExt != null) {
            val setCompileSdkVersion = androidExt.javaClass.methods.firstOrNull {
                it.name == "compileSdkVersion" && it.parameterCount == 1 && it.parameterTypes[0] == Int::class.javaPrimitiveType
            }
            setCompileSdkVersion?.invoke(androidExt, 36)
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
