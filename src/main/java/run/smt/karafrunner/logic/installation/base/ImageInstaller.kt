package run.smt.karafrunner.logic.installation.base

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.logic.manager.ImageManager
import run.smt.karafrunner.logic.KarafInstance
import java.io.File
import java.nio.file.Files

class ImageInstaller(
        private val targetInstance: KarafInstance,
        private val imageManager: ImageManager
) : BaseImageInstaller {

    val karafDir: File by lazy {
        if (imageManager.isCached) {
            imageManager.cachedKarafLocation
        } else {
            imageManager.mkCache()
        }
    }

    override fun install() {
        if (targetInstance.isInstalled) {
            return
        }
        val installationDir = Files.createTempDirectory("karaf-execution-").toFile()
        FileUtils.copyDirectory(karafDir, installationDir)
        targetInstance.updateInstallationPath(installationDir.absolutePath)
    }

}