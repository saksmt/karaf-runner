package run.smt.karafrunner.modules.impl.image

import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.logic.ImageManager
import run.smt.karafrunner.logic.installation.ImageInstaller
import run.smt.karafrunner.modules.impl.PathAwareModule

class DropCacheModule : PathAwareModule() {
    private val imageManager: ImageManager = ImageManager(karafVersion)

    override fun doRun() {
        imageManager.dropCache()
        success("Done")
    }
}