package run.smt.karafrunner.modules.api

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.error
import run.smt.karafrunner.modules.impl.VersionModule
import run.smt.karafrunner.modules.impl.util.TextProvidingModule

abstract class AbstractModule : Module {
    @Option(name = "--help", aliases = arrayOf("-h"), required = false)
    private var showHelp = false

    @Option(name = "--version", aliases = arrayOf("-V"), required = false)
    private var showVersion = false

    override fun run(arguments: List<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(arguments)
            if (showHelp) {
                TextProvidingModule(Thread.currentThread().contextClassLoader.getResource("help.txt").readText())
                    .run(arguments)
                return
            }
            if (showVersion) {
                VersionModule().run(arguments)
                return
            }
            doRun()
        } catch (e: UserErrorException) {
            error(e.message)
        } catch (e: CmdLineException) {
            error(e.localizedMessage)
        }
    }

    protected abstract fun doRun();
}