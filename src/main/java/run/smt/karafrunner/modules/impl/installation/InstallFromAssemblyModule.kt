package run.smt.karafrunner.modules.impl.installation

import run.smt.karafrunner.logic.provider.StubProvider
import run.smt.karafrunner.logic.provider.DeploymentFileProvider

open class InstallFromAssemblyModule : BaseInstallationModule() {
    override val deploymentProvider: DeploymentFileProvider = StubProvider()
    override var useAssembly: Boolean
        get() = true
        set(value) {}

    init {
        karafVersion = "assembly"
    }
}