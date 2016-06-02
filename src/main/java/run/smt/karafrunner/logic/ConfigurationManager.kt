package run.smt.karafrunner.logic

import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.input.prompt
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.util.Constants.pwd
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
        dependencies.flatMap { ConfigurationManager(File(it)).projects }.toSet() +
                if (projectsFromConfiguration.isEmpty()) {
                    linkedSetOf(guessProjectName())
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