package run.smt.karafrunner.modules.impl

import org.kohsuke.args4j.Option
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info

class GetPathModule : PathAwareModule() {
    @Option(name = "--raw", aliases = arrayOf("-r"))
    private var rawMode = false

    override fun doRun() {
        if (rawMode) {
            println(installationPathManager.installationPath)
        } else {
            info("Karaf is installed at ${installationPathManager.installationPath.hightlight()}")
        }
    }
}