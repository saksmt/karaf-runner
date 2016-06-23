package run.smt.karafrunner.os

import org.apache.commons.io.IOUtils
import run.smt.karafrunner.os.util.UnixUtils.UID
import run.smt.karafrunner.os.util.UnixUtils.isRoot

class Unix : Os {
    private object Paths: Os.Paths {
        override val binaryDirectory = "/usr/local/bin"
        override val temporaryDirectory = "/tmp"
        override val configurationDirectory = "/usr/local/etc"
        override val miscDirectory = "/opt"
        override val runDirectory by lazy {
            "/var/run" + if (isRoot) "" else "/user/$UID"
        }
    }

    override val name: String by lazy {
        IOUtils.toString(Runtime.getRuntime().exec("uname -or").inputStream)
    }

    override val isWindows: Boolean = false
    override val paths: Os.Paths = Paths
}