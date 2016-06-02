package run.smt.karafrunner.logic

import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.file.Path

class TemplateManager(
        private val templateFilesLocation: Path,
        private val env: String
) {
    fun copyProjectsTemplatesTo(projects: Set<String>, path: File) {
        installBaseTemplates(path)
        installProjectSkels(path, projects)
        installEnvSkel(path)
        installProjectConfig(path, projects)
    }

    private fun installProjectConfig(path: File, projects: Set<String>) {
        projects
                .map { File("$templateFilesLocation/project-config/$it/$env") }
                .filter { it.exists() && it.isDirectory }
                .forEach { FileUtils.copyDirectory(it, path) }
    }

    private fun installEnvSkel(path: File) {
        val sourcePath = File("$templateFilesLocation/skel/$env/")
        if (sourcePath.exists() && sourcePath.isDirectory) {
            FileUtils.copyDirectory(sourcePath, path)
        }
    }

    private fun installProjectSkels(path: File, projects: Set<String>) {
        projects
                .map { "$templateFilesLocation/project-skel/$it" }
                .map { File(it) }
                .filter { it.exists() && it.isDirectory }
                .forEach { FileUtils.copyDirectory(it, path) }
    }

    private fun installBaseTemplates(path: File) {
        val baseTemplates = templateFilesLocation.resolve("base").toFile()
        if (baseTemplates.isDirectory) {
            FileUtils.copyDirectory(baseTemplates, path)
        }
    }
}