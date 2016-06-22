package run.smt.karafrunner.modules.impl.installation

import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.provider.FeatureProvider

class InstallFromFeaturesModule : BaseInstallationModule() {
    override val deploymentProvider: DeploymentFileProvider by lazy {
        FeatureProvider()
    }
}