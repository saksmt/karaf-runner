package run.smt.karafrunner.modules.impl.installation

import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.provider.StubProvider

class InstallVanillaModule : BaseInstallationModule() {
    override val deploymentProvider: DeploymentFileProvider = StubProvider()
}