package run.smt.karafrunner.logic.util

import run.smt.karafrunner.io.input.getKey
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info

object Runner {
    fun run(installationPath: String, endless: Boolean, debug: Boolean) {
        var timeToExit: Boolean = false
        if (debug) {
            info("Running karaf in debug mode")
        }
        if (endless) {
            info("Running in endless mode")
        }
        do {
            ProcessBuilder("sh", "$installationPath/bin/karaf")
                    .inheritIO()
                    .apply {
                        if (debug) {
                            environment()["KARAF_DEBUG"] = "1"
                        }
                    }
                    .start()
                    .waitFor()
            if (endless) {
                info("Press any key to continue or one of ${"^C".hightlight()}, ${"^D".hightlight()}, ${"q".hightlight()} to exit... ", false)
                timeToExit = getKey() in arrayOf('q'.toInt(), 'Q'.toInt(), -1)
            }
        } while (endless && !timeToExit)
    }
}