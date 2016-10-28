package run.smt.karafrunner.io.input

import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.util.Filter

private val questionPrefix = ">>> ".hightlight()

fun prompt(question: String): Boolean {
    var answer: String?
    do {
        print(questionPrefix + question + " ")
        answer = readLine()
    } while (!(answer?.matches(Regex("[ynYN]?")) ?: true))
    return answer?.matches(Regex("[yY]?")) ?: true
}

fun getKey(): Int {
    return System.`in`.read()
}

fun <T> choose(question: String, options: Iterable<T>, toStringConverter: (T) -> String = { it.toString() }): List<T> {
    options.forEachIndexed { i, option ->
        println("  ${"[${i + 1}]".hightlight()} ${toStringConverter(option)}")
    }
    var answer: List<T>
    do {
        print(questionPrefix + question + " ")
        answer = askForList<T>().applyTo(options).toList()
    } while (answer.isEmpty())
    return answer
}

private fun <T> askForList(): Filter<T> {
    val answer = readLine().orEmpty()
    if (answer.all { it in arrayOf(',', ' ') }) {
        return Filter.first()
    }
    if (answer.equals("*")) {
        return Filter.all()
    }
    try {
        return if (answer.startsWith("-")) {
            Filter.allExcept(extractIndicies(answer.replace("-", "")))
        } else {
            Filter.some(extractIndicies(answer))
        }
    } catch (e: NumberFormatException) {
        return Filter.none()
    }
}

private fun extractIndicies(answer: String): List<Int> {
    return answer.split(",", " ")
            .filter { it.isNotBlank() }
            .map {
                it.trim().toInt() - 1
            }
}