package run.smt.karafrunner.modules.impl

import org.kohsuke.args4j.Option
import run.smt.karafrunner.logic.InstallationPathManager

abstract class PathAwareModule : BaseModule() {
    protected val installationPathManager: InstallationPathManager by lazy {
        return@lazy InstallationPathManager(karafVersion)
    }

    @Option(name = "--karaf-version", aliases = arrayOf("-k"))
    protected var karafVersion = "3"
}