package run.smt.karafrunner.io

import org.apache.commons.io.FileUtils
import java.io.File

fun File.copyToFile(file: File) {
    FileUtils.copyFile(this, file)
}

fun File.mkParentDirs(): Boolean {
    return parentFile.mkdirp()
}

/**
 * Create all necessary non-existent parent directories
 * @return false if directories are still missing, true otherwise
 *
 * Name of method inspired by `mkdir -p`
 */
fun File.mkdirp(): Boolean {
    if (exists()) {
        return true
    }
    return mkdirs()
}