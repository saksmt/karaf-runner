package run.smt.karafrunner.logic.util

import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.io.output.info
import run.smt.karafrunner.logic.util.PathRegistry.pwd
import java.io.File

object AssemblyUtils {
    fun locateAssembly(approximateAssemblyLocation: File = pwd): File {
        val found = approximateAssemblyLocation.walkTopDown().filter {
            it.absolutePath.endsWith("target/assembly") && it.isDirectory
        }.firstOrNull() ?: throw UserErrorException(
                "No assembly directory found! Did you forget to ${"maven clean install".hightlight()}?"
        )
        info("Found assembly directory at ${found.relativeTo(approximateAssemblyLocation).path.hightlight()}")
        return found
    }
}