package run.smt.karafrunner.modules.impl.util

import org.kohsuke.args4j.Argument
import run.smt.karafrunner.io.exception.UserErrorException
import run.smt.karafrunner.io.output.hightlight
import run.smt.karafrunner.modules.api.AbstractModule
import run.smt.karafrunner.modules.api.Module

class AggregateModule(private val modulesMapping: Map<String, Module>, defaultModule: String? = null) : AbstractModule() {
    @Argument()
    private var moduleName: String? = defaultModule

    private lateinit var arguments: List<String>;

    override fun run(arguments: List<String>) {
        this.arguments = arguments.drop(1)
        super.run(arguments.take(1))
    }

    private val noModule = "Unsupported operation. Use ${"help".hightlight()} for usage"

    override fun doRun() {
        val module = modulesMapping[moduleName] ?: throw UserErrorException(noModule)
        module.run(arguments)
    }
}