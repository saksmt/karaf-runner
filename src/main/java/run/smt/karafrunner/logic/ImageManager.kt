package run.smt.karafrunner.logic

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.logic.util.AssemblyUtils.locateAssembly
import java.io.File

class ImageManager(
        private val karafVersion: String
) {
    val cachedKarafLocation: File
        get() = File(InstallationPathManager.KARAF_CACHE_LOCATION.format(karafVersion))

    val isCached: Boolean by lazy {
        cachedKarafLocation.isDirectory
    }

    fun dropCache() {
        if (isCached) {
            cachedKarafLocation.deleteRecursively()
        }
    }

    fun updateImage(approximateImagePath: File) {
        val targetLocation = File(InstallationPathManager.KARAF_LOCATION.format(karafVersion))
        if (!targetLocation.canWrite()) {
            throw UserErrorException("You need superuser privileges to perform this operation!")
        }
        val assembly = locateAssembly(approximateImagePath)
        FileUtils.copyDirectory(assembly, targetLocation)
        dropCache()
    }

    fun mkCache(): File {
        FileUtils.copyDirectory(File(InstallationPathManager.KARAF_LOCATION.format(karafVersion)), cachedKarafLocation)
        return cachedKarafLocation
    }
}