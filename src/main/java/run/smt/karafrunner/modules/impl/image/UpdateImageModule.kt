package run.smt.karafrunner.modules.impl.image

import org.kohsuke.args4j.Argument
import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.logic.manager.ImageManager
import run.smt.karafrunner.logic.util.PathRegistry.pwd
import run.smt.karafrunner.modules.impl.InstanceAwareModule
import java.io.File

class UpdateImageModule : InstanceAwareModule() {
    @Argument(required = false)
    private var approximateImagePath: String? = null

    override fun doRun() {
        ImageManager(imageName).updateImage(approximateImagePath?.let { File(it) } ?: pwd)
        success("Done")
    }
}