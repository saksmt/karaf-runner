package run.smt.karafrunner.logic.manager

import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.input.prompt
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.util.PathRegistry.pwd
import run.smt.karafrunner.logic.util.ProParser
import java.io.File

class ConfigurationManager(private val path: File = pwd) {

    private val configuration: Map<String, Set<String>> by lazy {
        getConfiguration(path)
    }

    private val projectsFromConfiguration: Set<String> by lazy {
        configuration["projects"].orEmpty() + configuration["project"].orEmpty()
    }

    var defaultProjects: Set<String>? = null

    val dependencies: Set<String> by lazy {
        configuration["dependencies"].orEmpty() + configuration["dependency"].orEmpty()
    }

    val projects: Set<String> by lazy {
        if (projectsFromConfiguration.isEmpty()) {
            if (defaultProjects != null) {
                defaultProjects!!
            } else {
                linkedSetOf(guessProjectName())
            }
        } else {
            projectsFromConfiguration
        }
    }

    val images: Set<String> by lazy {
        configuration["images"].orEmpty() + configuration["image"].orEmpty()
    }

    private fun getConfiguration(path: File): Map<String, Set<String>> {
        return path.list()
                .find { it.matches("\\.karaf-runner\\.pro(ject)?$".toRegex()) }
                .let {
                    if (it == null) {
                        emptyMap()
                    } else {
                        val current = ProParser.parse(path.resolve(it))
                        merge(current,
                                (current["dependencies"].orEmpty() + current["dependency"].orEmpty())
                                        .map { getConfiguration(path.resolve(it)) }
                        )
                    }
                }
    }

    private fun merge(current: Map<String, Set<String>>, needToMerge: List<Map<String, Set<String>>>): Map<String, Set<String>> {
        return needToMerge.fold(current) { acc, now -> merge(acc, now) }
    }

    private fun merge(current: Map<String, Set<String>>, another: Map<String, Set<String>>): Map<String, Set<String>> {
        val result = hashMapOf<String, MutableSet<String>>()
        current.map { result.put(it.key, it.value.toMutableSet()) }
        another.map { result.putIfAbsent(it.key, linkedSetOf()).addAll(it.value) }
        return result
    }

    private fun guessProjectName(): String {
        val guessedName = path.list()
                .filter { it.contains("-") }
                .first()
                .takeWhile { it != '-' }
        info("Guessed project name is ${guessedName.hightlight()}")
        if (!prompt("Is it right?")) {
            throw UserErrorException("Can't deduce project name")
        }
        return guessedName
    }
}