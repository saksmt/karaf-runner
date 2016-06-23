package run.smt.karafrunner.modules.impl.installation

import org.kohsuke.args4j.Option
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.io.output.warning
import run.smt.karafrunner.logic.*
import run.smt.karafrunner.logic.installation.base.AssemblyInstaller
import run.smt.karafrunner.logic.installation.base.ImageInstaller
import run.smt.karafrunner.logic.installation.instance.Installer
import run.smt.karafrunner.logic.manager.ConfigurationManager
import run.smt.karafrunner.logic.manager.ImageManager
import run.smt.karafrunner.logic.manager.TemplateManager
import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import run.smt.karafrunner.modules.impl.InstanceAwareModule
import java.io.File

abstract class BaseInstallationModule : InstanceAwareModule() {
    @Option(name = "--env", aliases = arrayOf("--environment", "-e"), required = false)
    protected var env: String? = null

    @Option(name = "--use-assembly", aliases = arrayOf("-A"), required = false)
    protected open var useAssembly = false

    @Option(name = "--project-name", aliases = arrayOf("-p"), required = false)
    protected var projectNames: Array<String>? = null

    @Option(name = "--no-project", aliases = arrayOf("-P"), forbids = arrayOf("-p"))
    protected var noProject = false

    protected val imageManager by lazy {
        if (useAssembly) {
            AssemblyInstaller(targetInstance)
        } else {
            ImageInstaller(targetInstance, ImageManager(imageName))
        }
    }
    protected val templateManager by lazy {
        TemplateManager(File(templatesPath), env)
    }
    protected abstract val deploymentProvider: DeploymentFileProvider
    protected val configurationManager by lazy {
        ConfigurationManager()
    }
    protected val installer by lazy {
        Installer(configurationManager, templateManager, imageManager, deploymentProvider)
    }
    protected val preparedProjectNames: Set<String>? by lazy {
        if (noProject) {
            emptySet()
        } else {
            projectNames?.toSet()
        }
    }

    override fun doRun() {
        info("Installing...")
        if (env == null) {
            warning("Environment is not set! (use -e to set it)")
        }
        installer
            .install(targetInstance, env, preparedProjectNames)
        success("Installed!")
    }
}