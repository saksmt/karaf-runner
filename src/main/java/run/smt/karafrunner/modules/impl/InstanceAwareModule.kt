package run.smt.karafrunner.modules.impl

import org.kohsuke.args4j.Option
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.logic.KarafInstance
import run.smt.karafrunner.logic.manager.ConfigurationManager

abstract class InstanceAwareModule : BaseModule() {
    protected val targetInstance: KarafInstance by lazy {
        return@lazy KarafInstance(imageName)
    }

    @Option(name = "--image", aliases = arrayOf("-i"))
    private var userDefinedImageName: String? = null

    protected open val imageName by lazy {
        userDefinedImageName ?:
                if (configurationManager.images.size > 1) {
                    throw UserErrorException(
                            "Ambiguous image names in configuration, please specify it explicitly"
                    )
                } else if (configurationManager.images.isNotEmpty()) {
                    configurationManager.images.single()
                } else {
                    "default"
                }
    }

    protected val configurationManager by lazy {
        ConfigurationManager()
    }
}