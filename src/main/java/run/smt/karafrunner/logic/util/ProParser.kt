package run.smt.karafrunner.logic.util

import java.io.File

/**
 * Parses ".pro"-like format (qmake)
 */
object ProParser {
    fun parse(data: String): Map<String, Set<String>> {
        return data.split("\n")
            .map {
                it.split("+=")
            }
            .map {
                it.getOrNull(0)?.trim() to it.getOrNull(1)?.trim()
            }
            .filter {
                it.first != null && it.second != null
            }
            .groupBy { it.first!! }
            .mapValues { it.value.map { it.second!! }.toSet() }
    }

    fun parse(file: File): Map<String, Set<String>> = parse(file.readText())
}