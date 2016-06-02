package run.smt.karafrunner.modules.api

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.error

abstract class AbstractModule : Module {
    override fun run(arguments: List<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(arguments)
            doRun()
        } catch (e: UserErrorException) {
            error(e.message)
        } catch (e: CmdLineException) {
            error(e.localizedMessage)
        }
    }

    protected abstract fun doRun();
}