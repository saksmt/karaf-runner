package run.smt.karafrunner.logic.provider

import java.io.File

interface DeploymentFileProvider {
    fun provideDeploymentFilesFor(path: File): List<File>
}