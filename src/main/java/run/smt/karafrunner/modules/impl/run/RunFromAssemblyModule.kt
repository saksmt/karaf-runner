package run.smt.karafrunner.modules.impl.run

import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.provider.StubProvider

class RunFromAssemblyModule : BaseRunModule() {
    override val deploymentProvider: DeploymentFileProvider = StubProvider()
    override var useAssembly: Boolean
        get() = true
        set(value) {}

    init {
        karafVersion = "assembly"
    }
}