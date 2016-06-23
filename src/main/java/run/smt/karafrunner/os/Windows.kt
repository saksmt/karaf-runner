package run.smt.karafrunner.os

class Windows: Os {
    private object Paths: Os.Paths {
        override val binaryDirectory = "C:\\Windows\\System32"
        override val temporaryDirectory = System.getProperty("java.io.tmpdir")
        override val configurationDirectory = System.getProperty("user.home") + "\\.config"
        override val runDirectory = temporaryDirectory
        // I don't know what is the analogue in windows for /opt directory
        override val miscDirectory = System.getProperty("user.home") + "\\.opt"
    }

    override val name = "Micro\$oft Windows"
    override val paths: Os.Paths = Paths
    override val isWindows: Boolean = true
}