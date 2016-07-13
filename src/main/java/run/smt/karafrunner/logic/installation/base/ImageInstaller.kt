package run.smt.karafrunner.logic.installation.base

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.logic.manager.ImageManager
import run.smt.karafrunner.logic.KarafInstance
import run.smt.karafrunner.os.Os
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
        if (Os.get().isUnix) {
            installationDir.toPath().resolve("bin").resolve("karaf").toFile().setExecutable(true)
        }
        targetInstance.updateInstallationPath(installationDir.absolutePath)
    }

}