package run.smt.karafrunner.modules.impl

import run.smt.karafrunner.io.output.info
import java.io.File

class ShutdownModule: PathAwareModule() {
    override fun doRun() {
        if (installationPathManager.isInstalled) {
            File(installationPathManager.installationPath).deleteRecursively()
            installationPathManager.drop()
        }
        info("Shutdown")
    }
}