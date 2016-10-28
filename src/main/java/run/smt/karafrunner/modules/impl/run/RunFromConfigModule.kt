package run.smt.karafrunner.modules.impl.run

import run.smt.karafrunner.logic.provider.ConfiguredProvider
import run.smt.karafrunner.logic.provider.DeploymentFileProvider

class RunFromConfigModule : BaseRunModule() {
    override val deploymentProvider: DeploymentFileProvider by lazy {
        ConfiguredProvider(configurationManager)
    }
}