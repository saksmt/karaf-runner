package run.smt.karafrunner.modules.impl.installation

import run.smt.karafrunner.logic.provider.ConfiguredProvider
import run.smt.karafrunner.logic.provider.DeploymentFileProvider

class InstallFromConfigModule : BaseInstallationModule() {
    override val deploymentProvider: DeploymentFileProvider by lazy {
        ConfiguredProvider(configurationManager)
    }
}