package run.smt.karafrunner.logic.installation

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.logic.ImageManager
import run.smt.karafrunner.logic.InstallationPathManager
import java.io.File
import java.nio.file.Files

class ImageInstaller(
        private val pathManager: InstallationPathManager,
        private val imageManager: ImageManager
) : Installer {

    val karafDir: File by lazy {
        if (imageManager.isCached) {
            imageManager.cachedKarafLocation
        } else {
            imageManager.mkCache()
        }
    }

    override fun install() {
        if (pathManager.isInstalled) {
            return
        }
        val installationDir = Files.createTempDirectory("karaf-execution-").toFile()
        FileUtils.copyDirectory(karafDir, installationDir)
        pathManager.update(installationDir.absolutePath)
    }

}