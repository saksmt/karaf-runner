package run.smt.karafrunner.logic.manager

import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.input.prompt
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.util.PathRegistry.pwd
import run.smt.karafrunner.logic.util.ProParser
import java.io.File

class ConfigurationManager(private val path: File = pwd) {

    private val projectsFromConfiguration: Set<String> by lazy {
        path.list()
                .find { it == ".karaf-runner.project" }
                .let {
                    if (it == null) {
                        emptySet()
                    } else {
                        ProParser.parse(path.resolve(it)).getOrElse("projects") { emptySet() }
                    }
                }
    }

    var defaultProjects: Set<String>? = null

    val dependencies: Set<String> by lazy {
        path.list()
                .find { it == ".karaf-runner.project" }
                .let {
                    if (it == null) {
                        emptySet()
                    } else {
                        ProParser.parse(path.resolve(it)).getOrElse("dependencies") { emptySet() }
                    }
                }
    }

    val projects: Set<String> by lazy {
        dependencies.flatMap { ConfigurationManager(path.resolve(it)).projects }.toSet() +
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

    private fun guessProjectName(): String {
        val guessedName = path.list().first().takeWhile { it != '-' }
        info("Guessed project name is ${guessedName.hightlight()}")
        if (!prompt("Is it right?")) {
            throw UserErrorException("Can't deduce project name")
        }
        return guessedName
    }
}