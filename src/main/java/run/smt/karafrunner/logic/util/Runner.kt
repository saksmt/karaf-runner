package run.smt.karafrunner.logic.util

import run.smt.karafrunner.io.input.getKey
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.KarafInstance
import run.smt.karafrunner.logic.installation.instance.Installer
import run.smt.karafrunner.os.Os
import java.nio.file.Paths

object Runner {
    fun run(installer: Installer, targetInstance: KarafInstance, env: String?, projectNames: Set<String>?, endless: Boolean, debug: Boolean) {
        var timeToExit: Boolean = false
        if (debug) {
            info("Running karaf in debug mode")
        }
        if (endless) {
            info("Running in endless mode")
        }
        val executor: String
        val karaf: String

        if (Os.get().isUnix) {
            executor = "sh"
            karaf = "karaf"
        } else {
            executor = "java -jar"
            karaf = "karaf.bat"
        }

        do {
            ProcessBuilder(executor, Paths.get(targetInstance.installationPath, "bin", karaf).toString())
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
                if (!timeToExit) {
                    installer.installTemplates(targetInstance, env, projectNames)
                }
            }
        } while (endless && !timeToExit)
    }
}