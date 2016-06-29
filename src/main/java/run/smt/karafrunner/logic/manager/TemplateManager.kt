package run.smt.karafrunner.logic.manager

import org.apache.commons.io.FileUtils
import java.io.File

class TemplateManager(
        private val templateFilesLocation: File,
        private val env: String?
) {
    fun copyProjectsTemplatesTo(projects: Set<String>, path: File) {
        installBaseTemplates(path)
        installProjectSkels(path, projects)
        installEnvSkel(path)
        installProjectConfig(path, projects)
    }

    private fun installProjectConfig(path: File, projects: Set<String>) {
        if (env == null) {
            return
        }
        projects
                .map { templateFilesLocation.resolve("project-config").resolve(it).resolve(env) }
                .filter { it.exists() && it.isDirectory }
                .forEach { FileUtils.copyDirectory(it, path) }
    }

    private fun installEnvSkel(path: File) {
        if (env == null) {
            return
        }
        val sourcePath = templateFilesLocation.resolve("skel").resolve(env)
        if (sourcePath.exists() && sourcePath.isDirectory) {
            FileUtils.copyDirectory(sourcePath, path)
        }
    }

    private fun installProjectSkels(path: File, projects: Set<String>) {
        projects
                .map { templateFilesLocation.resolve("project-skel").resolve(it) }
                .filter { it.exists() && it.isDirectory }
                .forEach { FileUtils.copyDirectory(it, path) }
    }

    private fun installBaseTemplates(path: File) {
        val baseTemplates = templateFilesLocation.resolve("base")
        if (baseTemplates.isDirectory) {
            FileUtils.copyDirectory(baseTemplates, path)
        }
    }
}