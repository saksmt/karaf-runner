package run.smt.karafrunner.logic.util

import java.io.File

/**
 * Parses ".pro"-like format (qmake)
 */
object ProParser {
    fun parse(data: String): Map<String, Set<String>> {
        return data
            .split("\n")
            .map {
                it.split("#")[0]
            }
            .fold(mutableListOf<String>()) { acc, now ->
                if (acc.lastOrNull()?.endsWith("\\") ?: false)
                    acc[acc.lastIndex] = acc.last().dropLast(1) + "\n" + now
                else
                    acc += now
                acc
            }.asSequence()
            .map {
                it.split("\\+?=".toRegex())
            }
            .map {
                it.getOrNull(0)?.trim() to it.getOrNull(1)?.trim()
            }
            .map {
                it.first?.removeSurrounding("\"") to it.second?.removeSurrounding("\"")
            }
            .map {
                it.first?.removeSurrounding("'") to it.second?.removeSurrounding("'")
            }
            .map {
                it.first?.trim() to it.second?.trim()
            }
            .filterNot {
                it.first.isNullOrBlank() || it.second.isNullOrBlank()
            }
            .groupBy { it.first!! }
            .mapValues { it.value.map { it.second!! }.toSet() }
    }

    fun parse(file: File): Map<String, Set<String>> = parse(file.readText())
}