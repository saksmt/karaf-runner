package run.smt.karafrunner.logic.util

import run.smt.karafrunner.os.Os
import java.io.File
import java.nio.file.Paths

object PathRegistry {
    private val defaultConfiguration: Map<String, String> by lazy {
        mapOf(
                "templatesPath" to Paths.get(
                        Os.get().paths.configurationDirectory,
                        "karaf-runner"
                ).toString(),
                "imagesPath" to Paths.get(
                        Os.get().paths.miscDirectory,
                        "karaf-runner",
                        "images"
                ).toString()
        )
    }

    private val configuration: Map<String, String> by lazy {
        val result = hashMapOf<String, String>()
        result.putAll(defaultConfiguration)
        result.putAll(loadConfiguration())
        result
    }

    private fun loadConfiguration(): Map<String, String> {
        val configurationFile = File(System.getProperty(
                "karaf-runner.configurationFile",
                Paths.get(Os.Companion.get().paths.configurationDirectory, "karaf-runner", "config").toString()
        ))
        if (!configurationFile.canRead()) {
            return emptyMap()
        }
        return ProParser.parse(configurationFile).mapValues { it.value.last() }
    }

    val templatesPath: String by lazy { configuration["templatesPath"]!! }
    val imagesPath: String by lazy { configuration["imagesPath"]!! }
    val tmpPath: String by lazy { Paths.get(Os.get().paths.temporaryDirectory, "karaf-runner").toString() }

    val pwd: File by lazy {
        File(System.getProperty("user.dir"))
    }

    fun getPidFile(imageName: String): File {
        return Paths.get(Os.get().paths.runDirectory, "karaf-runner", "$imageName.pid").toFile()
    }
}