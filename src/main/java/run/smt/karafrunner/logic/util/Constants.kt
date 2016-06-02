package run.smt.karafrunner.logic.util

import java.io.File

object Constants {
    val pwd: File by lazy {
        File(System.getProperty("user.dir"))
    }
}