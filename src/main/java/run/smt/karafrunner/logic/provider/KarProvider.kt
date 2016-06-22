package run.smt.karafrunner.logic.provider

import run.smt.karafrunner.io.input.choose
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.provider.DeploymentFileProvider
import java.io.File

class KarProvider : DeploymentFileProvider {
    private val pwd: File by lazy {
        File(System.getProperty("user.dir"))
    }

    override fun provideDeploymentFilesFor(path: File): List<File> {
        val preChosen = path.walkTopDown()
                .filter { it.isFile }
                .filter { it.absolutePath.endsWith(".kar") }
                .filter { it.absolutePath.contains("target") }
        ;
        val show: (File) -> String = if (pwd.absolutePath == path.absolutePath) {
            { it.relativeTo(pwd).path }
        } else {
            { it.absolutePath }
        }
        info("Found ${".kar".hightlight()} files:")
        return choose("Choose kar file to use:", preChosen.asIterable(), show)
    }
}