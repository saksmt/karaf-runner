package run.smt.karafrunner.logic

import org.apache.commons.io.IOUtils
import run.smt.karafrunner.io.exception.UserErrorException
import java.io.File

class InstallationPathManager(private val karafVersion: String) {
    companion object {
        val KARAF_LOCATION = "/opt/karaf-%s.0"
        val KARAF_CACHE_LOCATION = "/tmp/karaf-%s.0"

        private val UID: String by lazy {
            return@lazy IOUtils.toString(Runtime.getRuntime().exec("id -u").inputStream).dropLast(1)
        }

        private val PID_FILE: String by lazy {
            return@lazy "/var/run" + if (UID == "1") "" else "/user/$UID"
        }
    }

    val pidFilePath: String by lazy {
        return@lazy "$PID_FILE/karaf-$karafVersion.0-dir"
    }

    private val pidFile: File by lazy {
        return@lazy File(pidFilePath)
    }

    val isInstalled: Boolean
        get() {
            return pidFile.canRead()
        }

    val installationPath: String
        get() {
            if (!isInstalled) {
                throw UserErrorException("Karaf is not running")
            }
            return pidFile.readText().replace("\n", "")
        }

    fun update(newPath: String) {
        pidFile.writeText(newPath)
    }

    fun drop() {
        pidFile.delete()
    }
}