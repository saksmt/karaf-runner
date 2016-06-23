package run.smt.karafrunner.modules.impl

import run.smt.karafrunner.io.output.info
import java.io.File

class ShutdownModule: InstanceAwareModule() {
    override fun doRun() {
        File(targetInstance.installationPath).deleteRecursively()
        targetInstance.drop()
        info("Shutdown")
    }
}