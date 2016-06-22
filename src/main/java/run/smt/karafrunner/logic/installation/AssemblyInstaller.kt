package run.smt.karafrunner.logic.installation

import org.apache.commons.io.FileUtils
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.util.Constants.pwd
import run.smt.karafrunner.logic.InstallationPathManager
import run.smt.karafrunner.logic.util.AssemblyUtils
import run.smt.karafrunner.logic.util.AssemblyUtils.locateAssembly
import java.io.File
import java.nio.file.Files

class AssemblyInstaller(private val installationPathManager: InstallationPathManager) : Installer {
    override fun install() {
        if (installationPathManager.isInstalled) {
            return
        }
        val installationDir = Files.createTempDirectory("karaf-execution-").toFile()
        val assemblyDir = locateAssembly()
        FileUtils.copyDirectory(assemblyDir, installationDir)
        installationPathManager.update(installationDir.absolutePath)
    }
}