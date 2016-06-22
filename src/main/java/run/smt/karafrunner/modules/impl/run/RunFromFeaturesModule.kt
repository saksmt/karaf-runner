package run.smt.karafrunner.modules.impl.run

import run.smt.karafrunner.logic.provider.FeatureProvider

class RunFromFeaturesModule : BaseRunModule() {
    override val deploymentProvider = FeatureProvider()
}