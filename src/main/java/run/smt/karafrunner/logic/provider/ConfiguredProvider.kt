package run.smt.karafrunner.logic.provider

import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.io.output.warning
import run.smt.karafrunner.logic.manager.ConfigurationManager
import java.io.File

class ConfiguredProvider(private val configurationManager: ConfigurationManager) : DeploymentFileProvider {
    override fun provideDeploymentFilesFor(path: File): List<File> {
        if (configurationManager.preInstall.isEmpty()) {
            warning("No files to install configured!")
            info("Will run as empty image")
        }
        return configurationManager.preInstall
            .map { path.resolve(it) }
    }
}