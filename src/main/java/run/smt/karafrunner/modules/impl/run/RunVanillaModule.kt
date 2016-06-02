package run.smt.karafrunner.modules.impl.run

import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.provider.StubProvider

class RunVanillaModule: BaseRunModule() {
    override val deploymentProvider: DeploymentFileProvider = StubProvider()
}