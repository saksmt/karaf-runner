package run.smt.karafrunner.modules.impl.image

import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.logic.manager.ImageManager
import run.smt.karafrunner.logic.installation.base.ImageInstaller
import run.smt.karafrunner.modules.impl.InstanceAwareModule

class DropCacheModule : InstanceAwareModule() {
    private val imageManager: ImageManager = ImageManager(imageName)

    override fun doRun() {
        imageManager.dropCache()
        success("Done")
    }
}