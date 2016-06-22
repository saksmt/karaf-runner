package run.smt.karafrunner.modules.impl.run

import run.smt.karafrunner.logic.provider.KarProvider

class RunFromKarsModule : BaseRunModule() {
    override val deploymentProvider = KarProvider()
}