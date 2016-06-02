package run.smt.karafrunner.io

import org.apache.commons.io.FileUtils
import java.io.File

fun File.copyToFile(file: File) {
    FileUtils.copyFile(this, file)
}