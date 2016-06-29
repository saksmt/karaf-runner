package run.smt.karafrunner.logic.provider

import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.input.choose
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.util.PathRegistry.pwd
import java.io.File

class KarProvider : DeploymentFileProvider {
    override fun provideDeploymentFilesFor(path: File): List<File> {
        val preChosen = path.walkTopDown()
                .filter { it.isFile }
                .filter { it.absolutePath.endsWith(".kar") }
                .filter { it.absolutePath.contains("target") }
                .asIterable()
        ;
        val show: (File) -> String = when (true) {
            pwd.absolutePath == path.absolutePath -> { { it.relativeTo(pwd).path } }
            !path.isAbsolute -> { { it.path } }
            else -> { { it.absoluteFile.absolutePath } }
        }
        if (preChosen.count() == 1) {
            info("Found ${show(preChosen.first()).hightlight()}")
            return preChosen.toList()
        } else if (preChosen.count() == 0) {
            throw UserErrorException("No ${".kar".hightlight()} files found!")
        }
        info("Found ${".kar".hightlight()} files:")
        return choose("Choose ${".kar".hightlight()} file to use:", preChosen, show)
    }
}