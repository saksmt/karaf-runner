package run.smt.karafrunner.modules.impl

import org.kohsuke.args4j.Option
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info

class GetPathModule : InstanceAwareModule() {
    @Option(name = "--raw", aliases = arrayOf("-r"))
    private var rawMode = false

    override fun doRun() {
        if (rawMode) {
            println(targetInstance.installationPath)
        } else {
            info("Karaf is installed at ${targetInstance.installationPath.hightlight()}")
        }
    }
}