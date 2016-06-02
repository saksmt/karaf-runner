package run.smt.karafrunner.logic.provider

import run.smt.karafrunner.io.input.choose
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.util.Constants.pwd
import java.io.File

class FeatureProvider : DeploymentFileProvider {
    override fun provideDeploymentFilesFor(path: File): List<File> {
        val preChosen = path.walkTopDown()
                .filter { it.isFile }
                .filter { it.absolutePath.endsWith("feature.xml") }
                .filter { it.absolutePath.contains("target") }
        ;
        val show: (File) -> String = if (pwd.absolutePath == path.absolutePath) {
            { it.relativeTo(pwd).path }
        } else {
            { it.absoluteFile.absolutePath }
        }
        info("Found ${".feature".hightlight()} files:")
        return choose("Choose feature file to use:", preChosen.asIterable(), show)
    }
}