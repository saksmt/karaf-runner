package run.smt.karafrunner.modules.impl.image

import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.logic.manager.ImageManager
import run.smt.karafrunner.logic.installation.base.ImageInstaller
import run.smt.karafrunner.modules.impl.InstanceAwareModule

class DropCacheModule : InstanceAwareModule() {
    override fun doRun() {
        info("Dropping cache for ${imageName.hightlight()}")
        ImageManager(imageName).dropCache()
        success("Done")
    }
}