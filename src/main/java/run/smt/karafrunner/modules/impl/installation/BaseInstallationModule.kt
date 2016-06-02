package run.smt.karafrunner.modules.impl.installation

import org.kohsuke.args4j.Option
import run.smt.karafrunner.io.copyToFile
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.logic.*
import run.smt.karafrunner.logic.installation.AssemblyInstaller
import run.smt.karafrunner.logic.installation.ImageInstaller
import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.logic.util.Constants
import run.smt.karafrunner.modules.impl.PathAwareModule
import java.io.File
import java.nio.file.FileSystems

abstract class BaseInstallationModule : PathAwareModule() {
    @Option(name = "--env", aliases = arrayOf("--environment", "-e"), required = true)
    protected var env: String? = null

    @Option(name = "--use-assembly", aliases = arrayOf("-A"), required = false)
    protected open var useAssembly = false

    protected val imageManager by lazy {
        if (useAssembly) {
            AssemblyInstaller(installationPathManager)
        } else {
            ImageInstaller(installationPathManager, karafVersion)
        }
    }
    protected val templateManager by lazy {
        TemplateManager(FileSystems.getDefault().getPath(templatesPath), env!!)
    }
    protected abstract val deploymentProvider: DeploymentFileProvider
    protected val configurationManager by lazy {
        ConfigurationManager()
    }

    override fun doRun() {
        info("Installing...")
        if (installationPathManager.isInstalled) {
            info("Already installed. Nothing to do...")
            return
        }
        if (env == null) {
            throw UserErrorException("Environment is not set!")
        }
        imageManager.install()
        templateManager.copyProjectsTemplatesTo(
                configurationManager.projects,
                File(installationPathManager.installationPath).resolve("etc")
        )
        (configurationManager.dependencies + Constants.pwd.absolutePath).flatMap {
            deploymentProvider.provideDeploymentFilesFor(File(it))
        }.forEach {
            it.copyToFile(File("${installationPathManager.installationPath}/deploy/${it.absolutePath.replace("/", "_")}"))
        }
        success("Installed!")
    }
}