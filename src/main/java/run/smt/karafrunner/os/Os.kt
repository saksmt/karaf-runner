package run.smt.karafrunner.os

interface Os {
    companion object {
        private val instance: Os by lazy {
            if (System.getProperty("os.name").startsWith("Windows")) {
                Windows()
            } else {
                Unix()
            }
        }

        fun get(): Os = instance
    }

    interface Paths {
        val binaryDirectory: String
        val temporaryDirectory: String
        val configurationDirectory: String
        val miscDirectory: String
        val runDirectory: String
    }
    val name: String
    val paths: Paths
    val isWindows: Boolean
    val isUnix: Boolean
        get() = !isWindows
}