package run.smt.karafrunner.logic

import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.mkParentDirs
import run.smt.karafrunner.logic.util.PathRegistry
import java.io.File

class KarafInstance(private val imageName: String) {
    private val pidFile: File by lazy {
        return@lazy PathRegistry.getPidFile(imageName)
    }

    val isInstalled: Boolean
        get() {
            return pidFile.canRead()
        }

    val installationPath: String
        get() {
            if (!isInstalled) {
                throw UserErrorException("Karaf is not running")
            }
            return pidFile.readText().replace("\n", "")
        }

    fun updateInstallationPath(newPath: String) {
        pidFile.mkParentDirs()
        pidFile.writeText(newPath)
    }

    fun drop() {
        pidFile.delete()
    }
}