package run.smt.karafrunner.modules.api

interface Module {
    fun run(arguments: List<String>)
}