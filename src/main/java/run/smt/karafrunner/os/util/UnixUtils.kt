package run.smt.karafrunner.os.util

import org.apache.commons.io.IOUtils

object UnixUtils {
    val UID: String by lazy {
        return@lazy IOUtils.toString(Runtime.getRuntime().exec("id -u").inputStream).dropLast(1)
    }

    val isRoot: Boolean by lazy {
        UID == "1"
    }

    val isUser: Boolean by lazy {
        !isRoot
    }
}