package run.smt.karafrunner.logic.installation.base

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.logic.KarafInstance
import run.smt.karafrunner.logic.util.AssemblyUtils.locateAssembly
import java.nio.file.Files

class AssemblyInstaller(private val targetInstance: KarafInstance) : BaseImageInstaller {
    override fun install() {
        if (targetInstance.isInstalled) {
            return
        }
        val installationDir = Files.createTempDirectory("karaf-execution-").toFile()
        val assemblyDir = locateAssembly()
        FileUtils.copyDirectory(assemblyDir, installationDir)
        targetInstance.updateInstallationPath(installationDir.absolutePath)
    }
}