package run.smt.karafrunner.modules.impl.run

import org.kohsuke.args4j.Option
import run.smt.karafrunner.logic.util.Runner
import run.smt.karafrunner.modules.impl.installation.BaseInstallationModule

abstract class BaseRunModule: BaseInstallationModule() {
    @Option(name = "--debug", aliases = arrayOf("-d"))
    protected var debug = false

    @Option(name = "--endless", aliases = arrayOf("-E"))
    protected var endless = false

    override fun doRun() {
        super.doRun()
        Runner.run(installer, targetInstance, env, preparedProjectNames, endless, debug)
    }
}