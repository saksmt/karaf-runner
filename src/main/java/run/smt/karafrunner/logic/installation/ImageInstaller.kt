package run.smt.karafrunner.logic.installation

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.logic.InstallationPathManager
import java.io.File
import java.nio.file.Files

class ImageInstaller(
        private val pathManager: InstallationPathManager,
        private val karafVersion: String
) : Installer {
    private val karafCachedLocation: File
        get() = File(InstallationPathManager.KARAF_CACHE_LOCATION.format(karafVersion))

    val karafDir: File by lazy {
        if (isCached) {
            karafCachedLocation
        } else {
            mkCache()
        }
    }

    val isCached: Boolean by lazy {
        File(InstallationPathManager.KARAF_CACHE_LOCATION.format(karafVersion)).isDirectory
    }

    fun reloadCache() {
        mkCache()
    }

    override fun install() {
        if (pathManager.isInstalled) {
            return
        }
        val installationDir = Files.createTempDirectory("karaf-execution-").toFile()
        FileUtils.copyDirectory(karafDir, installationDir)
        pathManager.update(installationDir.absolutePath)
    }

    private fun mkCache(): File {
        FileUtils.copyDirectory(File(InstallationPathManager.KARAF_LOCATION.format(karafVersion)), karafCachedLocation)
        return karafCachedLocation
    }

}