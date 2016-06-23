package run.smt.karafrunner.logic.installation.instance

import run.smt.karafrunner.io.copyToFile
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.manager.ConfigurationManager
import run.smt.karafrunner.logic.KarafInstance
import run.smt.karafrunner.logic.manager.TemplateManager
import run.smt.karafrunner.logic.installation.base.BaseImageInstaller
import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.util.PathRegistry.pwd
import java.io.File
import java.nio.file.Paths

class Installer(
        private val configurationManager: ConfigurationManager,
        private val templateManager: TemplateManager,
        private val baseImageInstaller: BaseImageInstaller,
        private val deploymentProvider: DeploymentFileProvider
) {
    fun installTemplates(targetInstance: KarafInstance, env: String?, projectNames: Set<String>?) {
        if (!targetInstance.isInstalled) {
            throw UserErrorException("Karaf is not installed!")
        }
        if (env != null) {
            info("Installing configuration templates for ${env.hightlight()} environment")
        } else {
            info("Installing configuration templates")
        }
        configurationManager.defaultProjects = projectNames
        templateManager.copyProjectsTemplatesTo(
                configurationManager.projects,
                File(targetInstance.installationPath).resolve("etc")
        )
    }

    fun install(targetInstance: KarafInstance, env: String?, projectNames: Set<String>?) {
        val installed = targetInstance.isInstalled
        if (!installed) {
            info("Installing base image")
            baseImageInstaller.install()
        } else {
            info("Base image already installed")
        }
        installTemplates(targetInstance, env, projectNames)
        if (!installed) {
            (configurationManager.dependencies + pwd.absolutePath).flatMap {
                deploymentProvider.provideDeploymentFilesFor(File(it))
            }.forEach {
                it.copyToFile(Paths.get(
                        targetInstance.installationPath,
                        "deploy",
                        it.toPath().joinToString("_")
                ).toFile())
            }
        }
    }
}