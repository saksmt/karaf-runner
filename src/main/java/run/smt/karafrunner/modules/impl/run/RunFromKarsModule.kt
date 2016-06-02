package run.smt.karafrunner.modules.impl.run

import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.provider.FeatureProvider

class RunFromKarsModule : BaseRunModule() {
    override val deploymentProvider: DeploymentFileProvider = FeatureProvider()
}