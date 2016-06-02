package run.smt.karafrunner.modules.impl.installation

import run.smt.karafrunner.logic.*
import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.provider.KarProvider

class InstallFromKarsModule : BaseInstallationModule() {
    override val deploymentProvider: DeploymentFileProvider by lazy {
        KarProvider()
    }
}