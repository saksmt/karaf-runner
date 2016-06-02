package run.smt.karafrunner.io.output

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi

fun String.hightlight(): String {
    return ansi().bold().render(this).reset().toString()
}

private fun coloredMessage(color: Ansi.Color, message: String)
        = ansi().bold().fg(color).render(" * ").reset().render(message)

fun success(message: String, newLine: Boolean = true) {
    coloredMessage(Ansi.Color.GREEN, message).run {
        if (newLine) {
            println(this)
        } else {
            print(this)
        }
    }
}

fun info(message: String, newLine: Boolean = true) {
    coloredMessage(Ansi.Color.CYAN, message).run {
        if (newLine) {
            println(this)
        } else {
            print(this)
        }
    }
}

fun warning(message: String, newLine: Boolean = true) {
    coloredMessage(Ansi.Color.YELLOW, message).run {
        if (newLine) {
            println(this)
        } else {
            print(this)
        }
    }
}

fun error(message: String, newLine: Boolean = true) {
    coloredMessage(Ansi.Color.RED, message).run {
        if (newLine) {
            println(this)
        } else {
            print(this)
        }
    }
}
