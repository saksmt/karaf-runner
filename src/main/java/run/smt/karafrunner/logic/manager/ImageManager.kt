package run.smt.karafrunner.logic.manager

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.mkdirp
import run.smt.karafrunner.logic.util.AssemblyUtils.locateAssembly
import run.smt.karafrunner.logic.util.PathRegistry
import run.smt.karafrunner.os.Os
import java.io.File
import java.nio.file.Paths

class ImageManager(
        private val imageName: String
) {
    private val imageDir = Paths.get(PathRegistry.imagesPath, imageName).toFile()
    val cachedKarafLocation: File
        get() = Paths.get(PathRegistry.tmpPath, "cache", imageName).toFile()

    val isCached: Boolean by lazy {
        cachedKarafLocation.isDirectory
    }

    fun dropCache() {
        if (isCached) {
            cachedKarafLocation.deleteRecursively()
        }
    }

    fun updateImage(approximateImagePath: File) {
        val targetLocation = imageDir
        if (!targetLocation.mkdirp() || !targetLocation.canWrite()) {
            throw UserErrorException("You need superuser privileges to perform this operation!")
        }
        val assembly = locateAssembly(approximateImagePath)
        FileUtils.copyDirectory(assembly, targetLocation)
        if (Os.get().isUnix) {
            targetLocation.toPath().resolve("bin").resolve("karaf").toFile().setExecutable(true)
        }
        dropCache()
    }

    fun mkCache(): File {
        FileUtils.copyDirectory(imageDir, cachedKarafLocation)
        return cachedKarafLocation
    }
}