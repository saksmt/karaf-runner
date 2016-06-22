package run.smt.karafrunner.modules.impl.image

import org.kohsuke.args4j.Argument
import run.smt.karafrunner.io.output.success
import run.smt.karafrunner.logic.ImageManager
import run.smt.karafrunner.logic.util.Constants.pwd
import run.smt.karafrunner.modules.impl.PathAwareModule
import java.io.File

class UpdateImageModule : PathAwareModule() {
    @Argument(required = false)
    private var approximateImagePath: String? = null

    override fun doRun() {
        ImageManager(karafVersion).updateImage(approximateImagePath?.let { File(it) } ?: pwd)
        success("Done")
    }
}