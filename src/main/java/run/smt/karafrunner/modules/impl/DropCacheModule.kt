package run.smt.karafrunner.modules.impl

import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.logic.installation.ImageInstaller

class DropCacheModule : PathAwareModule() {
    private val imageManager: ImageInstaller = ImageInstaller(installationPathManager, karafVersion)

    override fun doRun() {
        imageManager.reloadCache()
        success("Done")
    }
}