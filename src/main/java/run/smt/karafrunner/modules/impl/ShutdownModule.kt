package run.smt.karafrunner.modules.impl

import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.io.output.success
import java.io.File

class ShutdownModule: InstanceAwareModule() {
    override fun doRun() {
        info("Shutting down instance of ${imageName.hightlight()} image")
        File(targetInstance.installationPath).deleteRecursively()
        targetInstance.drop()
        success("Shutdown complete")
    }
}