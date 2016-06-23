package run.smt.karafrunner.modules.impl

import org.kohsuke.args4j.Option
import run.smt.karafrunner.logic.KarafInstance

abstract class InstanceAwareModule : BaseModule() {
    protected val targetInstance: KarafInstance by lazy {
        return@lazy KarafInstance(imageName)
    }

    @Option(name = "--image", aliases = arrayOf("-i"))
    protected var imageName = "default"
}