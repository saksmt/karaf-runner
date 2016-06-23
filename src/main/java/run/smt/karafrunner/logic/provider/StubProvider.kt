package run.smt.karafrunner.logic.provider

import java.io.File

class StubProvider : DeploymentFileProvider {
    override fun provideDeploymentFilesFor(path: File): List<File> {
        return emptyList()
    }
}